package com.VEMS.vems.other.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoAccess extends RuntimeException{
    private final String errorMsg;
}
