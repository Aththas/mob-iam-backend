package com.VEMS.vems.auth.dto.responseDto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
}
