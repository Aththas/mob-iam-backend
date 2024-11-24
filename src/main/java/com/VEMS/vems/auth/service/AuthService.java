package com.VEMS.vems.auth.service;

import com.VEMS.vems.auth.dto.requestDto.AuthDto;
import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.auth.dto.responseDto.AuthResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> signIn(SignInDto signInDto);

    ResponseEntity<?> authentication(AuthDto authDto);
}
