package com.VEMS.vems.other.exception;

import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleObjectNotValidException(ObjectNotValidException e){
        log.error("Empty or Null Object Value is Found: "+ e.getErrorMsg().toString());
        return new ResponseEntity<>(
                new ApiResponse<>(false, null, e.getErrorMsg().toString(), "400"),
                HttpStatus.BAD_REQUEST);
    }
}
