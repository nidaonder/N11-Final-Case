package com.nidaonder.restaurant.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {
    private final BaseErrorMessage baseErrorMessage;
}
