package com.VEMS.vems.dto.responseDto;

import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.entity.Visitor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViewVisitorEntryRequestDto {
    private Long id;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String nightStay;
    private String permission;
    private User user;
    private Visitor visitor;
}
