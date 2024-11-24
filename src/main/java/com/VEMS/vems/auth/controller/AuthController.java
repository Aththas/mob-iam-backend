package com.VEMS.vems.auth.controller;

import com.VEMS.vems.auth.dto.requestDto.AuthDto;
import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.auth.dto.responseDto.AuthResponseDto;
import com.VEMS.vems.auth.service.AuthService;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<?>> signIn(@RequestBody SignInDto signInDto){
        return authService.signIn(signInDto);
    }

    @PostMapping("/authentication")
    public ResponseEntity<ApiResponse<?>> authentication(@RequestBody AuthDto authDto){
        return authService.authentication(authDto);
    }
}
