package com.VEMS.vems.auth.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDto {
    @NotEmpty(message = "Email is Mandatory")
    @NotNull(message = "Email is Mandatory")
    private String email;

    @NotEmpty(message = "Password is Mandatory")
    @NotNull(message = "Password is Mandatory")
    private String password;
}
