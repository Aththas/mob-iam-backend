package com.VEMS.vems.controller;

import com.VEMS.vems.dto.requestDto.InternRecordInTimeDto;
import com.VEMS.vems.dto.requestDto.InternRecordOutTimeDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.InternEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internEntry")
@RequiredArgsConstructor
public class InternEntryController {

    private final InternEntryService internEntryService;
    @PostMapping("/recordInTime")
    public ResponseEntity<ApiResponse<?>> recordInTime(@RequestBody InternRecordInTimeDto recordInTimeDto){
        return internEntryService.recordInTime(recordInTimeDto);
    }

    @PutMapping("/recordOutTime")
    public ResponseEntity<ApiResponse<?>> recordOutTime(@RequestBody InternRecordOutTimeDto recordOutTimeDto){
        return internEntryService.recordOutTime(recordOutTimeDto);
    }

    @GetMapping("/viewInternEntries")
    public ResponseEntity<ApiResponse<?>> viewInternEntries(@RequestParam int page, @RequestParam int size,
                                                             @RequestParam String sortBy,
                                                             @RequestParam boolean ascending,
                                                             @RequestParam String fromDate,
                                                             @RequestParam String toDate){
        return internEntryService.viewInternEntries(page, size, sortBy, ascending, fromDate, toDate);
    }
}
