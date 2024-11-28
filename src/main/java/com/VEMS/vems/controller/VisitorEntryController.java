package com.VEMS.vems.controller;

import com.VEMS.vems.dto.requestDto.RecordInTimeDto;
import com.VEMS.vems.dto.requestDto.RecordOutTimeDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.VisitorEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/visitorEntry")
@RequiredArgsConstructor
public class VisitorEntryController {

    private final VisitorEntryService visitorEntryService;

    @PostMapping("/recordInTime")
    public ResponseEntity<ApiResponse<?>> recordInTime(@RequestBody RecordInTimeDto recordInTimeDto){
        return visitorEntryService.recordInTime(recordInTimeDto);
    }

    @PutMapping("/recordOutTime")
    public ResponseEntity<ApiResponse<?>> recordOutTime(@RequestBody RecordOutTimeDto recordOutTimeDto){
        return visitorEntryService.recordOutTime(recordOutTimeDto);
    }

    @GetMapping("/viewVisitorEntries")
    public ResponseEntity<ApiResponse<?>> viewVisitorEntries(@RequestParam int page, @RequestParam int size,
                                                             @RequestParam String sortBy,
                                                             @RequestParam boolean ascending,
                                                             @RequestParam String fromDate,
                                                             @RequestParam String toDate){
        return visitorEntryService.viewVisitorEntries(page, size, sortBy, ascending, fromDate, toDate);
    }
}
