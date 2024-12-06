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

    @GetMapping("/searchVisitorEntryRequestByUser")
    public ResponseEntity<ApiResponse<?>> searchVisitorEntryRequestByUser(@RequestParam int page, @RequestParam int size,
                                                                          @RequestParam String sortBy, @RequestParam boolean ascending,
                                                                          @RequestParam String keyword){
        return visitorService.searchVisitorEntryRequestByUser(page, size, sortBy, ascending, keyword);
    }

    @GetMapping("/viewPendingVisitorEntryRequest")
    public ResponseEntity<ApiResponse<?>> viewPendingVisitorEntryRequest(@RequestParam int page, @RequestParam int size,
                                                                         @RequestParam String sortBy,
                                                                         @RequestParam boolean ascending){
        return visitorService.viewPendingVisitorEntryRequest(page, size, sortBy, ascending);
    }

    @GetMapping("/viewAcceptVisitorEntryRequest")
    public ResponseEntity<ApiResponse<?>> viewAcceptVisitorEntryRequest(@RequestParam int page, @RequestParam int size,
                                                                         @RequestParam String sortBy,
                                                                         @RequestParam boolean ascending){
        return visitorService.viewAcceptVisitorEntryRequest(page, size, sortBy, ascending);
    }

    @GetMapping("/viewNotPendingVisitorEntryRequest")
    public ResponseEntity<ApiResponse<?>> viewNotPendingVisitorEntryRequest(@RequestParam int page, @RequestParam int size,
                                                                        @RequestParam String sortBy,
                                                                        @RequestParam boolean ascending){
        return visitorService.viewNotPendingVisitorEntryRequest(page, size, sortBy, ascending);
    }

    @PostMapping("/acceptVisitorRequestPermission")
    public ResponseEntity<ApiResponse<?>> acceptVisitorRequestPermission(@RequestParam Long id){
        return visitorService.acceptVisitorRequestPermission(id);
    }

    @PostMapping("/rejectVisitorRequestPermission")
    public ResponseEntity<ApiResponse<?>> rejectVisitorRequestPermission(@RequestParam Long id){
        return visitorService.rejectVisitorRequestPermission(id);
    }

    @GetMapping("/viewVisitorEntryByNic")
    public ResponseEntity<ApiResponse<?>> viewVisitorEntryByNic(@RequestParam String nic){
        return visitorService.viewVisitorEntryByNic(nic);
    }
}
