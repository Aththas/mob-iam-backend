package com.VEMS.vems.auth.service.impl;

import com.VEMS.vems.auth.dto.requestDto.AuthDto;
import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.auth.dto.responseDto.AuthResponseDto;
import com.VEMS.vems.auth.entity.token.Token;
import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.auth.repository.AuthRepository;
import com.VEMS.vems.auth.service.AuthService;
import com.VEMS.vems.config.JwtService;
import com.VEMS.vems.other.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    @Override
    public ResponseEntity<?> signIn(SignInDto signInDto) {
        if(signInDto == null)
            return new ResponseEntity<>("null object", HttpStatus.OK);

        Optional<User> optionalUser = authRepository.findByEmail(signInDto.getEmail());
        if(optionalUser.isPresent())
            return new ResponseEntity<>("User Already Exist", HttpStatus.OK);

        User user = authMapper.mapUser(signInDto);
        authRepository.save(user);

        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);

        authMapper.saveToken(accessToken,user);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(accessToken);
        authResponseDto.setRefreshToken(refreshToken);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> authentication(AuthDto authDto) {
        return null;
    }

}
