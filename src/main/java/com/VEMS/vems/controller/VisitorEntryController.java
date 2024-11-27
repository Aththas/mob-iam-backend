package com.VEMS.vems.controller;

import com.VEMS.vems.dto.requestDto.AddVisitorEntryDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.VisitorEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/visitorEntry")
@RequiredArgsConstructor
public class VisitorEntryController {

    private final VisitorEntryService visitorEntryService;

    @PostMapping("/recordInTime")
    public ResponseEntity<ApiResponse<?>> recordInTime(@RequestBody AddVisitorEntryDto addVisitorEntryDto){
        return visitorEntryService.recordInTime(addVisitorEntryDto);
    }
}
