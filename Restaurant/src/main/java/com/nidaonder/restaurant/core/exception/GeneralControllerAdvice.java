package com.nidaonder.restaurant.core.exception;

import com.nidaonder.restaurant.core.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException e, WebRequest request) {
        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessage(LocalDateTime.now(), message, description);
        var response = RestResponse.error(generalErrorMessage);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleItemExistException(ItemExistException e, WebRequest request) {
        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessage(LocalDateTime.now(), message, description);
        var response = RestResponse.error(generalErrorMessage);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
