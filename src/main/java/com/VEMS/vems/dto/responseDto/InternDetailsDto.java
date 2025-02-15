package com.VEMS.vems.dto.responseDto;

import lombok.Data;

@Data
public class InternDetailsDto {
    private String firstName;
    private String lastName;
    private String job;
    private String department;
    private String manager;
    private String intern;

    private String vehicleNo;
    private String inTime;
    private String outTime;
    private Long passNo;
}
