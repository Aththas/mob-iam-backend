package com.VEMS.vems.controller;

import com.VEMS.vems.dto.requestDto.ParentVisitorDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping("/addVisitorRequest")
    public ResponseEntity<ApiResponse<?>> addVisitorRequest(@RequestBody ParentVisitorDto parentVisitorDto){
        return visitorService.addVisitorRequest(parentVisitorDto);
    }
}
