package com.VEMS.vems.other.mapper;

import com.VEMS.vems.dto.requestDto.InternRecordInTimeDto;
import com.VEMS.vems.entity.InternEntry;
import com.VEMS.vems.other.timeFormatConfig.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InternEntryMapper {

    private final TimeFormatter timeFormatter;

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
}
