package com.VEMS.vems.service;

import com.VEMS.vems.dto.requestDto.RecordInTimeDto;
import com.VEMS.vems.dto.requestDto.RecordOutTimeDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface VisitorEntryService {
    ResponseEntity<ApiResponse<?>> recordInTime(RecordInTimeDto recordInTimeDto);

    ResponseEntity<ApiResponse<?>> recordOutTime(RecordOutTimeDto recordOutTimeDto);

    ResponseEntity<ApiResponse<?>> viewVisitorEntries(int page, int size, String sortBy, boolean ascending, String fromDate, String toDate);

    ResponseEntity<ApiResponse<?>> searchVisitorEntries(int page, int size, String sortBy, boolean ascending, String fromDate, String toDate, String keyword);
}
