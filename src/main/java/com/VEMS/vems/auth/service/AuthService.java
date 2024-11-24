package com.VEMS.vems.auth.service;

import com.VEMS.vems.auth.dto.requestDto.AuthDto;
import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.auth.dto.responseDto.AuthResponseDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponse<?>> signIn(SignInDto signInDto);

    ResponseEntity<ApiResponse<?>> authentication(AuthDto authDto);
}
