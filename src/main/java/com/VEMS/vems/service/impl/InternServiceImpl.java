package com.VEMS.vems.service.impl;

import com.VEMS.vems.dto.responseDto.InternDetailsDto;
import com.VEMS.vems.entity.InternEntry;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.exception.NoAccess;
import com.VEMS.vems.other.internApi.InternApi;
import com.VEMS.vems.repository.InternEntryRepository;
import com.VEMS.vems.service.InternService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {

    private final InternEntryRepository internEntryRepository;
    private final InternApi internApi;
    @Override
    public ResponseEntity<ApiResponse<?>> viewInternDetails(String username) {
        try{
            InternDetailsDto internDetails = internApi.getInternDetailsByUsername(username);

            Optional<InternEntry> optionalInternEntry
                    = internEntryRepository.findByDateAndIntern(LocalDate.now(), username);
            if(optionalInternEntry.isPresent()){
                InternEntry internEntry = optionalInternEntry.get();
                internDetails.setPassNo(internEntry.getPassNo());
                internDetails.setVehicleNo(internEntry.getVehicleNo());
                internDetails.setInTime(internEntry.getInTime());
                internDetails.setOutTime(internEntry.getOutTime());
            }
            String msg = "View intern Details: " + internDetails.getFirstName() + " " + internDetails.getLastName();
            log.info(msg);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, internDetails, msg, null),
                    HttpStatus.OK);

        }catch (NoAccess e){
            throw e;
        }
        catch (Exception e){
            log.error("view intern Details: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
