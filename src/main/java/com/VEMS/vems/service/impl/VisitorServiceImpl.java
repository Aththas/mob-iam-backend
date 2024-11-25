package com.VEMS.vems.service.impl;

import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.auth.service.impl.UserServiceImpl;
import com.VEMS.vems.dto.requestDto.AddVisitorDto;
import com.VEMS.vems.dto.requestDto.AddVisitorEntryRequestDto;
import com.VEMS.vems.dto.requestDto.ParentVisitorDto;
import com.VEMS.vems.entity.Visitor;
import com.VEMS.vems.entity.VisitorEntryRequest;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.mapper.VisitorMapper;
import com.VEMS.vems.other.validator.ObjectValidator;
import com.VEMS.vems.repository.VisitorEntryRequestRepository;
import com.VEMS.vems.repository.VisitorRepository;
import com.VEMS.vems.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final ObjectValidator<AddVisitorDto> addVisitorDtoObjectValidator;
    private final ObjectValidator<AddVisitorEntryRequestDto> addVisitorEntryRequestDtoObjectValidator;
    private final VisitorMapper visitorMapper;
    private final VisitorEntryRequestRepository visitorEntryRequestRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse<?>> addVisitorRequest(ParentVisitorDto parentVisitorDto) {

        User user = userServiceImpl.getCurrentUser();
        if(user == null){
            log.error("User Not Found");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "User Not Found", "404"),
                    HttpStatus.OK);
        }

        AddVisitorDto addVisitorDto = parentVisitorDto.getAddVisitorDto();
        AddVisitorEntryRequestDto addVisitorEntryRequestDto = parentVisitorDto.getAddVisitorEntryRequestDto();

        if(addVisitorDto == null || addVisitorEntryRequestDto == null){
            log.error("add visitor request: null object");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "null object", "400"),
                    HttpStatus.OK);
        }

        addVisitorDtoObjectValidator.validate(addVisitorDto);
        addVisitorEntryRequestDtoObjectValidator.validate(addVisitorEntryRequestDto);

        try {
            Visitor visitor;
            Optional<Visitor> optionalVisitor = visitorRepository.findByVerificationId(addVisitorDto.getVerificationId());
            if(optionalVisitor.isEmpty()){
                visitor = visitorMapper.mapVisitor(addVisitorDto);
                visitorRepository.save(visitor);
                log.info("add visitor request: visitor added " + visitor.getName());
            }else{
                visitor = optionalVisitor.get();
                log.info("add visitor request: visitor exist " + visitor.getName());

                List<VisitorEntryRequest> visitorEntryRequestList = visitorEntryRequestRepository.findAllByVisitorId(visitor.getId());
                if(!visitorEntryRequestList.isEmpty()){
                    boolean alreadyHaveAccess = visitorEntryRequestList
                            .stream()
                            .anyMatch(visit -> visit.getEndDate().isAfter(addVisitorEntryRequestDto.getStartDate()));

                    if(alreadyHaveAccess){
                        final String msg = "add visitor request: Already Having Entry Request for " + visitor.getName()
                                + " Up to " + addVisitorEntryRequestDto.getStartDate();
                        log.error(msg);
                        return new ResponseEntity<>(
                                new ApiResponse<>(false, null, msg, "409"),
                                HttpStatus.OK);
                    }
                }
            }
            VisitorEntryRequest visitorEntryRequest = visitorMapper.mapVisitorEntryRequest(addVisitorEntryRequestDto, visitor, user);
            visitorEntryRequestRepository.save(visitorEntryRequest);

            log.info("add visitor request: successfully added");
            return new ResponseEntity<>(
                    new ApiResponse<>(true, null,"Add Visitor Request Successfully", null),
                    HttpStatus.OK);

        }catch (Exception e){
            log.error("add visitor request: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
