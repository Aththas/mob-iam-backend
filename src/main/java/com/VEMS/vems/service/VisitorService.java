package com.VEMS.vems.service;

import com.VEMS.vems.dto.requestDto.ParentVisitorDto;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface VisitorService {
    ResponseEntity<ApiResponse<?>> addVisitorRequest(ParentVisitorDto parentVisitorDto);

    ResponseEntity<ApiResponse<?>> viewVisitorEntryRequestByUser(int page, int size, String sortBy, boolean ascending);

    ResponseEntity<ApiResponse<?>> viewPendingVisitorEntryRequest(int page, int size, String sortBy, boolean ascending);

    ResponseEntity<ApiResponse<?>> viewAcceptVisitorEntryRequest(int page, int size, String sortBy, boolean ascending);

    ResponseEntity<ApiResponse<?>> acceptVisitorRequestPermission(Long id);

    ResponseEntity<ApiResponse<?>> rejectVisitorRequestPermission(Long id);

    ResponseEntity<ApiResponse<?>> viewNotPendingVisitorEntryRequest(int page, int size, String sortBy, boolean ascending);

    ResponseEntity<ApiResponse<?>> viewVisitorEntryByNic(String nic);

    ResponseEntity<ApiResponse<?>> searchVisitorEntryRequestByUser(int page, int size, String sortBy, boolean ascending, String keyword);

    ResponseEntity<ApiResponse<?>> searchAcceptVisitorEntryRequest(int page, int size, String sortBy, boolean ascending, String keyword);
}
