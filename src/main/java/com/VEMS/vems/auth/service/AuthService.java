package com.VEMS.vems.auth.service;

import com.VEMS.vems.auth.dto.requestDto.AuthDto;
import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponse<?>> signIn(SignInDto signInDto);

    ResponseEntity<ApiResponse<?>> authentication(AuthDto authDto);

    ResponseEntity<ApiResponse<?>> refreshToken(HttpServletRequest request);
}
