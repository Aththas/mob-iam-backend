package com.VEMS.vems.controller;

import com.VEMS.vems.dto.DummyRequestDto;
import com.VEMS.vems.dto.DummyResponseDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dummyInternDetails")
@Slf4j
public class DummyController {

    @PostMapping
    public ResponseEntity<ApiResponse<?>> dummyInternDetails(@RequestBody DummyRequestDto dummyRequestDto){
        try{

            DummyResponseDto responseDto = new DummyResponseDto();
            responseDto.setIntern(dummyRequestDto.getUsername());
            responseDto.setFirstname("Aththas");
            responseDto.setLastname("Rizwan");
            responseDto.setJob("Software engineer");
            responseDto.setDepartment("Information Systems");
            responseDto.setManager("Musthalie");

            return new ResponseEntity<>(
                    new ApiResponse<>(true, responseDto, "success", null),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("dummy request: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
