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
import com.VEMS.vems.other.pagination.PaginationConfig;
import com.VEMS.vems.other.validator.ObjectValidator;
import com.VEMS.vems.repository.VisitorEntryRequestRepository;
import com.VEMS.vems.repository.VisitorRepository;
import com.VEMS.vems.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
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
    private final PaginationConfig paginationConfig;

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

                    ResponseEntity<ApiResponse<?>> dateRangeValidation = validateDateRange(addVisitorEntryRequestDto);
                    if(dateRangeValidation != null){
                        return dateRangeValidation;
                    }
                    boolean alreadyHaveAccess = visitorEntryRequestList
                            .stream()
                            .anyMatch(visit ->
                                    (
                                            !visit.getPermission().equals("reject") &&
                                            addVisitorEntryRequestDto.getStartDate().isBefore(visit.getEndDate()) &&
                                            addVisitorEntryRequestDto.getEndDate().isAfter(visit.getStartDate())
                                    ));

                    if(alreadyHaveAccess){
                        final String msg = "The provided date range From " + addVisitorEntryRequestDto.getStartDate() + " To "
                                + addVisitorEntryRequestDto.getEndDate() + " overlaps with an existing entry request";
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

    private ResponseEntity<ApiResponse<?>> validateDateRange(AddVisitorEntryRequestDto addVisitorEntryRequestDto){
        if(addVisitorEntryRequestDto.getEndDate().isBefore(addVisitorEntryRequestDto.getStartDate())){
            log.error("add visitor request: Invalid Date Range");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Invalid Date Range", "301"),
                    HttpStatus.OK);
        }

        long dateRange = ChronoUnit.DAYS.between(
                addVisitorEntryRequestDto.getStartDate(),
                addVisitorEntryRequestDto.getEndDate()
        );
        if(dateRange > 30) {
            log.error("add visitor request: Date Range Should be with in a Month");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Date Range Should be with in a Month", "302"),
                    HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> viewVisitorEntryRequestByUser(int page, int size, String sortBy, boolean ascending) {
        User user = userServiceImpl.getCurrentUser();
        if(user == null){
            log.error("User Not Found");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "User Not Found", "404"),
                    HttpStatus.OK);
        }

        try{
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            Page<VisitorEntryRequest> visitorEntryRequestPerPage =
                    visitorEntryRequestRepository.findAllByUserId(user.getId(), pageable);

            return displayVisitorEntryRequests(visitorEntryRequestPerPage);

        }catch (Exception e){
            log.error("add visitor request: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse<?>> displayVisitorEntryRequests(Page<VisitorEntryRequest> visitorEntryRequestPerPage){
        long count = visitorEntryRequestPerPage.getTotalElements();

        if (visitorEntryRequestPerPage.isEmpty()) {
            log.error("display Visitor Entry Request: Empty List");
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Empty List", "404"),
                    HttpStatus.OK);
        }

        log.info("display Visitor Entry Request: Data Retrieved");
        return new ResponseEntity<>(
                new ApiResponse<>(true,
                        visitorEntryRequestPerPage.stream().map(visitorMapper::mapViewVisitorEntryRequest).toList(),
                        Long.toString(count), null),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> viewPendingVisitorEntryRequest(int page, int size, String sortBy, boolean ascending) {
        try{
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            return viewVisitorEntryRequestByPermission(pageable, "pending");
        }catch (Exception e){
            log.error("Entry Request By Permission: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<ApiResponse<?>> viewAcceptVisitorEntryRequest(int page, int size, String sortBy, boolean ascending) {
        try{
            Pageable pageable = paginationConfig.getPageable(page, size, sortBy, ascending);

            return viewVisitorEntryRequestByPermission(pageable, "accept");
        }catch (Exception e){
            log.error("Entry Request By Permission: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse<?>> viewVisitorEntryRequestByPermission(Pageable pageable, String permission) {
        Page<VisitorEntryRequest> visitorEntryRequestsByPermission =
                visitorEntryRequestRepository.findAllByPermission(permission, pageable);

        return displayVisitorEntryRequests(visitorEntryRequestsByPermission);
    }
}
