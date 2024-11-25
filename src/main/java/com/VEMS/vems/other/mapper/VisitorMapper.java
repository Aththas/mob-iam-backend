package com.VEMS.vems.other.mapper;

import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.dto.requestDto.AddVisitorDto;
import com.VEMS.vems.dto.requestDto.AddVisitorEntryRequestDto;
import com.VEMS.vems.dto.responseDto.ViewVisitorEntryRequestDto;
import com.VEMS.vems.entity.Visitor;
import com.VEMS.vems.entity.VisitorEntryRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class VisitorMapper {


    public Visitor mapVisitor(AddVisitorDto addVisitorDto) {
        Visitor visitor = new Visitor();
        visitor.setName(addVisitorDto.getName());
        visitor.setCompany(addVisitorDto.getCompany());
        visitor.setVerificationId(addVisitorDto.getVerificationId());
        return visitor;
    }

    public VisitorEntryRequest mapVisitorEntryRequest(AddVisitorEntryRequestDto addVisitorEntryRequestDto, Visitor visitor, User user) {
        VisitorEntryRequest visitorEntryRequest = new VisitorEntryRequest();
        visitorEntryRequest.setDepartment(addVisitorEntryRequestDto.getDepartment());
        visitorEntryRequest.setStartDate(addVisitorEntryRequestDto.getStartDate());
        visitorEntryRequest.setEndDate(addVisitorEntryRequestDto.getEndDate());
        visitorEntryRequest.setNightStay(addVisitorEntryRequestDto.getNightStay());
        visitorEntryRequest.setUser(user);
        visitorEntryRequest.setVisitor(visitor);
        visitorEntryRequest.setPermission("pending");
        return visitorEntryRequest;
    }

    public ViewVisitorEntryRequestDto mapViewVisitorEntryRequest(VisitorEntryRequest visitorEntryRequest) {
        ViewVisitorEntryRequestDto viewVisitorEntryRequestDto = new ViewVisitorEntryRequestDto();
        viewVisitorEntryRequestDto.setId(visitorEntryRequest.getId());
        viewVisitorEntryRequestDto.setDepartment(visitorEntryRequest.getDepartment());
        viewVisitorEntryRequestDto.setStartDate(visitorEntryRequest.getStartDate());
        viewVisitorEntryRequestDto.setEndDate(visitorEntryRequest.getEndDate());
        viewVisitorEntryRequestDto.setNightStay(visitorEntryRequest.getNightStay());
        viewVisitorEntryRequestDto.setUser(visitorEntryRequest.getUser());
        viewVisitorEntryRequestDto.setVisitor(visitorEntryRequest.getVisitor());
        viewVisitorEntryRequestDto.setPermission(visitorEntryRequest.getPermission());
        return viewVisitorEntryRequestDto;
    }
}
