package com.VEMS.vems.service.impl;

import com.VEMS.vems.dto.requestDto.InternRecordInTimeDto;
import com.VEMS.vems.dto.requestDto.InternRecordOutTimeDto;
import com.VEMS.vems.entity.InternEntry;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.exception.NoAccess;
import com.VEMS.vems.other.mapper.InternEntryMapper;
import com.VEMS.vems.other.timeFormatConfig.TimeFormatter;
import com.VEMS.vems.other.validator.ObjectValidator;
import com.VEMS.vems.repository.InternEntryRepository;
import com.VEMS.vems.service.InternEntryService;
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
public class InternEntryServiceImpl implements InternEntryService {

    private final ObjectValidator<InternRecordInTimeDto> recordInTimeDtoObjectValidator;
    private final ObjectValidator<InternRecordOutTimeDto> recordOutTimeDtoObjectValidator;
    private final InternEntryRepository internEntryRepository;
    private final InternEntryMapper internEntryMapper;
    private final TimeFormatter timeFormatter;
    @Override
    public ResponseEntity<ApiResponse<?>> recordInTime(InternRecordInTimeDto recordInTimeDto) {
        recordInTimeDtoObjectValidator.validate(recordInTimeDto);
        try{
//            recordInTimeDto.getIntern() goes through api verification again if failed throw no access exception
            if(!recordInTimeDto.getIntern().equalsIgnoreCase("aththas")){
                throw new NoAccess("Access Denied");
            }

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
            //            recordInTimeDto.getIntern() goes through api verification again if failed throw no access exception
            if(!recordOutTimeDto.getIntern().equalsIgnoreCase("aththas")){
                throw new NoAccess("Access Denied");
            }

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
}
