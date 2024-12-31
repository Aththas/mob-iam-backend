package com.VEMS.vems.service;

import com.VEMS.vems.dto.requestDto.InternRecordInTimeDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface InternEntryService {
    ResponseEntity<ApiResponse<?>> recordInTime(InternRecordInTimeDto recordInTimeDto);
}
