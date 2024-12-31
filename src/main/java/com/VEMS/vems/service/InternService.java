package com.VEMS.vems.service;

import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface InternService {
    ResponseEntity<ApiResponse<?>> viewInternDetails(String username);
}
