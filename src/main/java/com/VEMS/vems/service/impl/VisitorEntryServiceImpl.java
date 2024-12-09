package com.VEMS.vems.service.impl;

import com.VEMS.vems.dto.requestDto.RecordInTimeDto;
import com.VEMS.vems.dto.requestDto.RecordOutTimeDto;
import com.VEMS.vems.entity.VisitorEntry;
import com.VEMS.vems.entity.VisitorEntryRequest;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.mapper.VisitorEntryMapper;
import com.VEMS.vems.other.pagination.PaginationConfig;
import com.VEMS.vems.other.timeFormatConfig.TimeFormatter;
import com.VEMS.vems.other.validator.ObjectValidator;
import com.VEMS.vems.repository.VisitorEntryRepository;
import com.VEMS.vems.repository.VisitorEntryRequestRepository;
import com.VEMS.vems.service.VisitorEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorEntryServiceImpl implements VisitorEntryService {

    private final VisitorEntryRepository visitorEntryRepository;
    private final VisitorEntryMapper visitorEntryMapper;
    private final VisitorEntryRequestRepository visitorEntryRequestRepository;
    private final TimeFormatter timeFormatter;
    private final PaginationConfig paginationConfig;
    private final ObjectValidator<RecordInTimeDto> recordInTimeDtoObjectValidator;
    private final ObjectValidator<RecordOutTimeDto> recordOutTimeDtoObjectValidator;
    @Override
    @CacheEvict(value = "visitorEntries", allEntries = true)
    public ResponseEntity<ApiResponse<?>> recordInTime(RecordInTimeDto recordInTimeDto) {
        recordInTimeDtoObjectValidator.validate(recordInTimeDto);
        try{
            Optional<VisitorEntryRequest> optionalVisitorEntryRequest
                    = visitorEntryRequestRepository.findById(recordInTimeDto.getVisitorEntryRequestId());

            if(optionalVisitorEntryRequest.isEmpty()){
                log.error("record in: Invalid Access Request");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Invalid Access Request", "404"),
                        HttpStatus.OK);

            } else if (!optionalVisitorEntryRequest.get().getPermission().equals("accept") ||
                    optionalVisitorEntryRequest.get().getStartDate().isAfter(LocalDate.now()) ||
                    optionalVisitorEntryRequest.get().getEndDate().isBefore(LocalDate.now())
            ) {
                String msg = "No Access Found for "+ optionalVisitorEntryRequest.get().getVisitor().getName() + " for today";
                log.error(msg);
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, msg, "404"),
                        HttpStatus.OK);
            }
            VisitorEntryRequest validVisitorEntryRequest = optionalVisitorEntryRequest.get();

            Optional<VisitorEntry> optionalVisitorEntry =
                    visitorEntryRepository.findByDateAndVisitorEntryRequestId(LocalDate.now(),
                            validVisitorEntryRequest.getId());

            if(optionalVisitorEntry.isPresent()){
                log.error("record in : already recorded");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Already Recorded", "409"),
                        HttpStatus.OK);
            }

            VisitorEntry visitorEntry = visitorEntryMapper.mapNewVisitorEntry(recordInTimeDto, validVisitorEntryRequest);
            visitorEntryRepository.save(visitorEntry);
            String msg = "Successfully recorded " + visitorEntry.getVisitorEntryRequest().getVisitor().getName()
                            + " (in time) entry at " + visitorEntry.getInTime();
            log.info(msg);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, null, msg, null),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("record in: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @CacheEvict(value = "visitorEntries", allEntries = true)
    public ResponseEntity<ApiResponse<?>> recordOutTime(RecordOutTimeDto recordOutTimeDto) {
        recordOutTimeDtoObjectValidator.validate(recordOutTimeDto);
        try{
            Optional<VisitorEntryRequest> optionalVisitorEntryRequest
                    = visitorEntryRequestRepository.findById(recordOutTimeDto.getVisitorEntryRequestId());

            if(optionalVisitorEntryRequest.isEmpty()){
                log.error("record in: Invalid Access Request");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Invalid Access Request", "404"),
                        HttpStatus.OK);

            } else if (!optionalVisitorEntryRequest.get().getPermission().equals("accept") ||
                    optionalVisitorEntryRequest.get().getStartDate().isAfter(LocalDate.now()) ||
                    optionalVisitorEntryRequest.get().getEndDate().isBefore(LocalDate.now())
            ) {
                String msg = "No Access Found for "+ optionalVisitorEntryRequest.get().getVisitor().getName() + " for today";
                log.error(msg);
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, msg, "404"),
                        HttpStatus.OK);
            }
            VisitorEntryRequest validVisitorEntryRequest = optionalVisitorEntryRequest.get();

            Optional<VisitorEntry> optionalVisitorEntry =
                    visitorEntryRepository.findByDateAndVisitorEntryRequestId(LocalDate.now(),
                            validVisitorEntryRequest.getId());

            if(optionalVisitorEntry.isEmpty()){
                log.error("record out : Not Yet recorded the in time");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Not Yet Recorded the in time", "409"),
                        HttpStatus.OK);
            }
            VisitorEntry validVisitorEntry = optionalVisitorEntry.get();
            if(validVisitorEntry.getOutTime() != null){
                log.error("record out : already recorded");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Already Recorded", "409"),
                        HttpStatus.OK);
            }

            validVisitorEntry.setOutTime(timeFormatter.currentTime());
            visitorEntryRepository.save(validVisitorEntry);

            String msg = "Successfully recorded " + validVisitorEntry.getVisitorEntryRequest().getVisitor().getName()
                    + " (out time) entry at " + validVisitorEntry.getOutTime();
            log.info(msg);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, null, msg, null),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("record out: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "visitorEntries", key = "#page + '-' + #size + '-' + #sortBy + '-' + #ascending + '-' + #fromDate + '-' + #toDate")
    public ResponseEntity<ApiResponse<?>> viewVisitorEntries(int page, int size, String sortBy, boolean ascending, String fromDate, String toDate) {
        try{
            LocalDate startDate = LocalDate.parse(fromDate);
            LocalDate endDate = LocalDate.parse(toDate);
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            Page<VisitorEntry> visitorEntriesPerPage =
                    visitorEntryRepository.findAllByDateBetween(startDate, endDate, pageable);

            return displayVisitorEntries(visitorEntriesPerPage);

        }catch (Exception e){
            log.error("View Visitor Entries: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "visitorEntries", key = "#page + '-' + #size + '-' + #sortBy + '-' + #ascending + '-' + #fromDate + '-' + #toDate + '_' + #keyword")
    public ResponseEntity<ApiResponse<?>> searchVisitorEntries(int page, int size, String sortBy, boolean ascending, String fromDate, String toDate, String keyword) {
        try{
            LocalDate startDate = LocalDate.parse(fromDate);
            LocalDate endDate = LocalDate.parse(toDate);
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            Page<VisitorEntry> visitorEntries =
                    visitorEntryRepository.searchAllByDateBetweenAndKeyword(keyword, startDate, endDate, pageable);

            return displayVisitorEntries(visitorEntries);

        }catch (Exception e){
            log.error("View Visitor Entries: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse<?>> displayVisitorEntries(Page<VisitorEntry> visitorEntries) {
        long count = visitorEntries.getTotalElements();

        if (visitorEntries.isEmpty()) {
            log.error("display Visitor Entries: Empty List");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Empty List", "404"),
                    HttpStatus.OK);
        }

        log.info("display Visitor Entries: Data Retrieved");
        return new ResponseEntity<>(
                new ApiResponse<>(true,
                        visitorEntries.stream().map(visitorEntryMapper::mapViewVisitorEntry).toList(),
                        Long.toString(count), null),
                HttpStatus.OK);
    }
}
