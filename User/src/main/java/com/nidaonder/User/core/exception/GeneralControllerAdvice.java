package com.nidaonder.User.core.exception;

import com.nidaonder.User.core.RestResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object>  handleItemNotFoundException(ItemNotFoundException e, WebRequest request) {
        var response = getGeneralErrorMessageRestResponse(e.getBaseErrorMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleItemExistException(ItemExistException e, WebRequest request) {
        var response = getGeneralErrorMessageRestResponse(e.getBaseErrorMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTException(BusinessException e, WebRequest request) {
        var response = getGeneralErrorMessageRestResponse(e.getBaseErrorMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllException(GlobalException e, WebRequest request) {
        var response = getGeneralErrorMessageRestResponse(e.getBaseErrorMessage(), request);

        log.warn("Unexpected error occurred!");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private RestResponse<GeneralErrorMessage> getGeneralErrorMessageRestResponse(BaseErrorMessage e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessage(LocalDateTime.now(), message, description);

        return RestResponse.error(generalErrorMessage);
    }
}
