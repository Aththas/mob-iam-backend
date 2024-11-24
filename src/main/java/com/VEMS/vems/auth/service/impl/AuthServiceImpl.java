package com.VEMS.vems.auth.service.impl;

import com.VEMS.vems.auth.dto.requestDto.AuthDto;
import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.auth.dto.responseDto.AuthResponseDto;
import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.auth.repository.AuthRepository;
import com.VEMS.vems.auth.service.AuthService;
import com.VEMS.vems.config.JwtService;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.mapper.AuthMapper;
import com.VEMS.vems.other.validator.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final ObjectValidator<SignInDto> signInDtoObjectValidator;
    @Override
    public ResponseEntity<ApiResponse<?>> signIn(SignInDto signInDto) {
        if(signInDto == null){
            log.error("sign in: null object");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "null object", "400"),
                    HttpStatus.OK);
        }

        signInDtoObjectValidator.validate(signInDto);

        Optional<User> optionalUser = authRepository.findByEmail(signInDto.getEmail());
        if(optionalUser.isPresent()){
            log.error("sign in: User Already Exist");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "User Already Exist", "409"),
                    HttpStatus.OK);
        }

        try{
            User user = authMapper.mapUser(signInDto);
            authRepository.save(user);

            final String accessToken = jwtService.generateToken(user);
            final String refreshToken = jwtService.generateRefreshToken(user);

            authMapper.saveToken(accessToken,user);

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccessToken(accessToken);
            authResponseDto.setRefreshToken(refreshToken);

            log.info("Sign in: Success " + user.getEmail());
            return new ResponseEntity<>(
                    new ApiResponse<>(true, authResponseDto, "Successfully signed in", null),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("sign in: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "SERVER ERROR", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> authentication(AuthDto authDto) {
        return null;
    }

}
