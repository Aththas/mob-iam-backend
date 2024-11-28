package com.VEMS.vems.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AddVisitorEntryRequestDto {
    @NotEmpty(message = "Allowed Department is Mandatory")
    @NotNull(message = "Allowed Department is Mandatory")
    private String department;

    @NotNull(message = "Invalid Start Date")
    private LocalDate startDate;

    @NotNull(message = "Invalid End Date")
    private LocalDate endDate;

    @NotEmpty(message = "Marking Night Stay is Mandatory")
    @NotNull(message = "Marking Night Stay is Mandatory")
    private String nightStay;
}
