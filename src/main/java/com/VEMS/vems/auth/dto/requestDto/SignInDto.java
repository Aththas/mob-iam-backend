package com.VEMS.vems.auth.dto.requestDto;

import lombok.Data;

@Data
public class SignInDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
