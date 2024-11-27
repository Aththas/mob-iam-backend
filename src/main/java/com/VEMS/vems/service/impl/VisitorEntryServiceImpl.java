package com.VEMS.vems.service.impl;

import com.VEMS.vems.dto.requestDto.AddVisitorEntryDto;
import com.VEMS.vems.entity.VisitorEntry;
import com.VEMS.vems.entity.VisitorEntryRequest;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.mapper.VisitorEntryMapper;
import com.VEMS.vems.repository.VisitorEntryRepository;
import com.VEMS.vems.repository.VisitorEntryRequestRepository;
import com.VEMS.vems.service.VisitorEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public ResponseEntity<ApiResponse<?>> recordInTime(AddVisitorEntryDto addVisitorEntryDto) {
        try{
            Optional<VisitorEntryRequest> optionalVisitorEntryRequest
                    = visitorEntryRequestRepository.findById(addVisitorEntryDto.getVisitorEntryRequestId());

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

            VisitorEntry visitorEntry = visitorEntryMapper.mapNewVisitorEntry(addVisitorEntryDto, validVisitorEntryRequest);
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
}
