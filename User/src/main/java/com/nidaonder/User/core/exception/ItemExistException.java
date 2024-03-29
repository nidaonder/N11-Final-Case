package com.nidaonder.User.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemExistException extends BusinessException{
    public ItemExistException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}
