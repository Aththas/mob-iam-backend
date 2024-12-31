package com.VEMS.vems.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InternRecordInTimeDto {
    private String vehicleNo;

    @Positive(message = "Invalid Pass No")
    @NotNull(message = "Invalid Pass No")
    private Long passNo;

    @NotEmpty(message = "Invalid Intern Username")
    @NotNull(message = "Invalid Intern Username")
    private String intern;
}
