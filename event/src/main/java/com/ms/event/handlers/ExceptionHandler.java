package com.ms.event.handlers;

import com.ms.event.exceptions.EventNotFoundException;
import com.ms.event.exceptions.SubscriptionNotAllowedException;
import com.ms.event.exceptions.SubscriptionNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({EventNotFoundException.class, SubscriptionNotAllowedException.class, SubscriptionNotFoundException.class})
    public ResponseEntity<Object> handleUserException(
            Exception exception, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ErrorResponseModel exceptionResponseMessage =
                new ErrorResponseModel(
                        httpStatus.value(),
                        LocalDateTime.now(),
                        exception.getMessage(),
                        ((ServletWebRequest) request).getRequest().getRequestURI()
                );

        return handleExceptionInternal(
                exception,
                exceptionResponseMessage,
                new HttpHeaders(),
                httpStatus,
                request
        );
    }

}
