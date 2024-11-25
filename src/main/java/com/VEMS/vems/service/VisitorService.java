package com.VEMS.vems.service;

import com.VEMS.vems.dto.requestDto.ParentVisitorDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface VisitorService {
    ResponseEntity<ApiResponse<?>> addVisitorRequest(ParentVisitorDto parentVisitorDto);

    ResponseEntity<ApiResponse<?>> viewVisitorEntryRequestByUser(int page, int size, String sortBy, boolean ascending);
}
