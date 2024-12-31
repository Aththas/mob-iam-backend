package com.VEMS.vems.controller;

import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.service.InternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/intern")
@RequiredArgsConstructor
public class InternController {

    private final InternService internService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> viewInternDetails(@RequestParam String username){
        return internService.viewInternDetails(username);
    }
}
