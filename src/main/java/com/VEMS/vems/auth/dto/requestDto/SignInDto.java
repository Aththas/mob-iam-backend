package com.VEMS.vems.auth.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInDto {

    @NotEmpty(message = "Firstname is Mandatory")
    @NotNull(message = "Firstname is Mandatory")
    private String firstName;

    @NotEmpty(message = "Lastname is Mandatory")
    @NotNull(message = "Lastname is Mandatory")
    private String lastName;

    @NotEmpty(message = "Email is Mandatory")
    @NotNull(message = "Email is Mandatory")
    private String email;

    @NotEmpty(message = "Password is Mandatory")
    @NotNull(message = "Password is Mandatory")
    private String password;

    @NotEmpty(message = "Role is Mandatory")
    @NotNull(message = "Role is Mandatory")
    private String role;
}
