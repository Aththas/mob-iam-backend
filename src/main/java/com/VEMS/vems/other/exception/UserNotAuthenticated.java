package com.VEMS.vems.other.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotAuthenticated extends RuntimeException{
    private final String errorMsg;
}
