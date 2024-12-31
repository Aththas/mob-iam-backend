package com.VEMS.vems.service.impl;

import com.VEMS.vems.dto.requestDto.InternRecordInTimeDto;
import com.VEMS.vems.dto.requestDto.InternRecordOutTimeDto;
import com.VEMS.vems.entity.InternEntry;
import com.VEMS.vems.entity.VisitorEntry;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.exception.NoAccess;
import com.VEMS.vems.other.internApi.InternApi;
import com.VEMS.vems.other.mapper.InternEntryMapper;
import com.VEMS.vems.other.pagination.PaginationConfig;
import com.VEMS.vems.other.timeFormatConfig.TimeFormatter;
import com.VEMS.vems.other.validator.ObjectValidator;
import com.VEMS.vems.repository.InternEntryRepository;
import com.VEMS.vems.service.InternEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternEntryServiceImpl implements InternEntryService {

    private final ObjectValidator<InternRecordInTimeDto> recordInTimeDtoObjectValidator;
    private final ObjectValidator<InternRecordOutTimeDto> recordOutTimeDtoObjectValidator;
    private final InternEntryRepository internEntryRepository;
    private final InternEntryMapper internEntryMapper;
    private final TimeFormatter timeFormatter;
    private final PaginationConfig paginationConfig;
    private final InternApi internApi;
    @Override
    public ResponseEntity<ApiResponse<?>> recordInTime(InternRecordInTimeDto recordInTimeDto) {
        recordInTimeDtoObjectValidator.validate(recordInTimeDto);
        try{
            internApi.verifyAPI(recordInTimeDto.getIntern());

            Optional<InternEntry> optionalInternEntry =
                    internEntryRepository.findByDateAndIntern(LocalDate.now(), recordInTimeDto.getIntern());

            if(optionalInternEntry.isPresent()){
                log.error("record in : already recorded");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Already Recorded", "409"),
                        HttpStatus.OK);
            }

            InternEntry internEntry = internEntryMapper.mapNewInternEntry(recordInTimeDto);
            internEntryRepository.save(internEntry);

            String msg = "Successfully recorded " + internEntry.getIntern() + " (in time) entry at " + internEntry.getInTime();
            log.info(msg);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, null, msg, null),
                    HttpStatus.OK);

        }catch (NoAccess e){
            throw e;
        }
        catch (Exception e){
            log.error("record in: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> recordOutTime(InternRecordOutTimeDto recordOutTimeDto) {
        recordOutTimeDtoObjectValidator.validate(recordOutTimeDto);
        try{
            internApi.verifyAPI(recordOutTimeDto.getIntern());

            Optional<InternEntry> optionalInternEntry =
                    internEntryRepository.findByDateAndIntern(LocalDate.now(), recordOutTimeDto.getIntern());

            if(optionalInternEntry.isEmpty()){
                log.error("record out : Not Yet recorded the in time");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Not Yet Recorded the in time", "409"),
                        HttpStatus.OK);
            }
            InternEntry internEntry = optionalInternEntry.get();
            if(internEntry.getOutTime() != null){
                log.error("record out : already recorded");
                return new ResponseEntity<>(
                        new ApiResponse<>(false, null, "Already Recorded", "409"),
                        HttpStatus.OK);
            }

            internEntry.setOutTime(timeFormatter.currentTime());
            internEntryRepository.save(internEntry);
            String msg = "Successfully recorded " + internEntry.getIntern() + " (out time) entry at " + internEntry.getOutTime();
            log.info(msg);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, null, msg, null),
                    HttpStatus.OK);

        }catch (NoAccess e){
            throw e;
        }
        catch (Exception e){
            log.error("record in: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> viewInternEntries(int page, int size, String sortBy, boolean ascending, String fromDate, String toDate) {
        try{
            LocalDate startDate = LocalDate.parse(fromDate);
            LocalDate endDate = LocalDate.parse(toDate);
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            Page<InternEntry> internEntries =
                    internEntryRepository.findAllByDateBetween(startDate, endDate, pageable);

            return displayInternEntries(internEntries);

        }catch (Exception e){
            log.error("View Intern Entries: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> searchInternEntries(int page, int size, String sortBy, boolean ascending, String fromDate, String toDate, String keyword) {
        try{
            LocalDate startDate = LocalDate.parse(fromDate);
            LocalDate endDate = LocalDate.parse(toDate);
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            Page<InternEntry> internEntries =
                    internEntryRepository.searchAllByDateBetweenAndKeyword(keyword, startDate, endDate, pageable);

            return displayInternEntries(internEntries);

        }catch (Exception e){
            log.error("View Visitor Entries: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse<?>> displayInternEntries(Page<InternEntry> internEntries) {
        long count = internEntries.getTotalElements();

        if (internEntries.isEmpty()) {
            log.error("display Intern Entries: Empty List");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Empty List", "404"),
                    HttpStatus.OK);
        }

        log.info("display Intern Entries: Data Retrieved");
        return new ResponseEntity<>(
                new ApiResponse<>(true,
                        internEntries.stream().map(internEntryMapper::mapViewInternEntry).toList(),
                        Long.toString(count), null),
                HttpStatus.OK);
    }
}
