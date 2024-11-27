package com.VEMS.vems.service;

import com.VEMS.vems.dto.requestDto.AddVisitorEntryDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface VisitorEntryService {
    ResponseEntity<ApiResponse<?>> recordInTime(AddVisitorEntryDto addVisitorEntryDto);
}
