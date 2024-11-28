package com.VEMS.vems.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecordInTimeDto {

    @NotEmpty(message = "Vehicle No is Mandatory")
    @NotNull(message = "Vehicle No is Mandatory")
    private String vehicleNo;

    @Positive(message = "Invalid Pass No")
    @NotNull(message = "Invalid Pass No")
    private Long passNo;

    @Positive(message = "Invalid Visitor Entry Request Id")
    @NotNull(message = "Invalid Visitor Entry Request Id")
    private Long visitorEntryRequestId;
}
