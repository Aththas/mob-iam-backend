package com.VEMS.vems.other.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectNotValidException extends RuntimeException{
    private final Set<String> errorMsg;
}
