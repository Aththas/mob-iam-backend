package com.VEMS.vems.service.impl;

import com.VEMS.vems.dto.responseDto.InternDetailsDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.InternService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InternServiceImpl implements InternService {
    @Override
    public ResponseEntity<ApiResponse<?>> viewInternDetails(String username) {
        if(!username.equals("aththas")){
            log.info("Access Denied");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Access Denied", "401"),
                    HttpStatus.OK);
        }

        InternDetailsDto internDetailsDto = new InternDetailsDto();
        internDetailsDto.setFirstName("Aththas");
        internDetailsDto.setLastName("Rizwan");
        internDetailsDto.setJob("Information Security");
        internDetailsDto.setDepartment("Information Systems");
        internDetailsDto.setManager("Musthalie");

        log.info("User has Access for today");
        return new ResponseEntity<>(
                new ApiResponse<>(true, internDetailsDto, "User has Access for today", null),
                HttpStatus.OK);
    }
}
