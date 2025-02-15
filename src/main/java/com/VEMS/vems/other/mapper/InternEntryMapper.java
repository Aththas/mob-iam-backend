package com.VEMS.vems.other.mapper;

import com.VEMS.vems.dto.requestDto.InternRecordInTimeDto;
import com.VEMS.vems.dto.responseDto.InternDetailsDto;
import com.VEMS.vems.dto.responseDto.ViewInternEntryDto;
import com.VEMS.vems.entity.InternEntry;
import com.VEMS.vems.other.internApi.InternApi;
import com.VEMS.vems.other.timeFormatConfig.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InternEntryMapper {

    private final TimeFormatter timeFormatter;
    private final InternApi internApi;

    public InternEntry mapNewInternEntry(InternRecordInTimeDto recordInTimeDto) {
        InternEntry internEntry = new InternEntry();
        internEntry.setIntern(recordInTimeDto.getIntern());
        internEntry.setDate(LocalDate.now());
        internEntry.setOutTime(null);
        internEntry.setInTime(timeFormatter.currentTime());
        internEntry.setPassNo(recordInTimeDto.getPassNo());
        internEntry.setVehicleNo(recordInTimeDto.getVehicleNo());
        return internEntry;
    }

    public ViewInternEntryDto mapViewInternEntry(InternEntry internEntry) {
        ViewInternEntryDto viewInternEntryDto = new ViewInternEntryDto();
        viewInternEntryDto.setDate(internEntry.getDate());
        viewInternEntryDto.setInTime(internEntry.getInTime());
        viewInternEntryDto.setOutTime(internEntry.getOutTime());
        viewInternEntryDto.setVehicleNo(internEntry.getVehicleNo());
        viewInternEntryDto.setPassNo(internEntry.getPassNo());
        viewInternEntryDto.setIntern(internEntry.getIntern());

        //get response from intern verification API
        InternDetailsDto internDetails = internApi.getInternDetailsByUsername(internEntry.getIntern());
        viewInternEntryDto.setJob(internDetails.getJob());
        viewInternEntryDto.setDepartment(internDetails.getDepartment());
        viewInternEntryDto.setManager(internDetails.getManager());
        return viewInternEntryDto;
    }
}
