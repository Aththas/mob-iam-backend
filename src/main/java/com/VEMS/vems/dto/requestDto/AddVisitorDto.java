package com.VEMS.vems.dto.requestDto;

import lombok.Data;

@Data
public class AddVisitorDto {
    private String name;
    private String company;
    private String verificationId;
}
