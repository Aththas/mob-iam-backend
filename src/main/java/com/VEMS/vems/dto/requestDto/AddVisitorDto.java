package com.VEMS.vems.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddVisitorDto {
    @NotEmpty(message = "Visitor Name is Mandatory")
    @NotNull(message = "Visitor Name is Mandatory")
    private String name;

    @NotEmpty(message = "Company is Mandatory")
    @NotNull(message = "Company is Mandatory")
    private String company;

    @NotEmpty(message = "Verification ID/NIC is Mandatory")
    @NotNull(message = "Verification ID/NIC is Mandatory")
    private String verificationId;
}
