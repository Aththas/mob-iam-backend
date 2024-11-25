package com.VEMS.vems.controller;

import com.VEMS.vems.dto.requestDto.ParentVisitorDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping("/addVisitorRequest")
    public ResponseEntity<ApiResponse<?>> addVisitorRequest(@RequestBody ParentVisitorDto parentVisitorDto){
        return visitorService.addVisitorRequest(parentVisitorDto);
    }

    @GetMapping("/viewVisitorEntryRequestByUser")
    public ResponseEntity<ApiResponse<?>> viewVisitorEntryRequestByUser(@RequestParam int page, @RequestParam int size,
                                                             @RequestParam String sortBy,
                                                             @RequestParam boolean ascending){
        return visitorService.viewVisitorEntryRequestByUser(page, size, sortBy, ascending);
    }
}
