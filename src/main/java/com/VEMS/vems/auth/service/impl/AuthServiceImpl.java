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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final ObjectValidator<SignInDto> signInDtoObjectValidator;
    private final AuthenticationManager authenticationManager;
    private final ObjectValidator<AuthDto> authDtoObjectValidator;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse<?>> authentication(AuthDto authDto) {
        if(authDto == null){
            log.error("Auth: null object");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "null object", "400"),
                    HttpStatus.OK);
        }

        authDtoObjectValidator.validate(authDto);

        Optional<User> optionalUser = authRepository.findByEmail(authDto.getEmail());
        if(optionalUser.isEmpty()){
            log.error("Auth: User Not Found");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Authentication Failure", "404"),
                    HttpStatus.OK);
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getEmail(),
                            authDto.getPassword()
                    )
            );

            User user = optionalUser.get();

            authMapper.removeToken(user.getId());

            final String accessToken = jwtService.generateToken(user);
            final String refreshToken = jwtService.generateRefreshToken(user);

            authMapper.saveToken(accessToken, user);

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccessToken(accessToken);
            authResponseDto.setRefreshToken(refreshToken);

            log.info("Auth: User Logged In " + user.getEmail());
            return new ResponseEntity<>(
                    new ApiResponse<>(true, authResponseDto, user.getRole().toString(), null),
                    HttpStatus.OK);

        }catch (BadCredentialsException e){
            log.error("Authentication: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Authentication Failure", "401"),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("Authentication: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse<?>> refreshToken(HttpServletRequest request) {
        try{
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                log.error("refresh token: Invalid Refresh Token Type");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Invalid Refresh Token Type", "404"),
                        HttpStatus.OK);
            }

            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = authRepository.findByEmail(userEmail);
            if(optionalUser.isEmpty()){
                log.error("refresh token: User Not Found");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Invalid Refresh Token", "404"),
                        HttpStatus.OK);
            }

            if(jwtService.isTokenExpired(jwt)){
                log.error("refresh token: Refresh Token Expired");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Invalid Refresh Token", "404"),
                        HttpStatus.OK);
            }

            User user = optionalUser.get();

            authMapper.removeToken(user.getId());

            final String accessToken = jwtService.generateRefreshToken(user);

            authMapper.saveToken(accessToken, user);

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccessToken(accessToken);
            authResponseDto.setRefreshToken(jwt);

            log.info("Refresh token: Refresh Token Generated");
            return new ResponseEntity<>(
                    new ApiResponse<>(true, authResponseDto, "Refresh Token Generated", null),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("Refresh Token: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
