package com.VEMS.vems.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InternRecordOutTimeDto {
    @Positive(message = "Invalid Intern Username")
    @NotNull(message = "Invalid Intern Username")
    private String intern;
}
