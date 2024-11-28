package com.VEMS.vems.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecordOutTimeDto {
    @Positive(message = "Invalid Visitor Entry Request Id")
    @NotNull(message = "Invalid Visitor Entry Request Id")
    private Long visitorEntryRequestId;
}
