package com.totvs.tjc.infra.rest.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.totvs.tjc.infra.rest.advice.ExceptionResponse.ConstraintViolationResponse;
import com.totvs.tjc.infra.rest.advice.ExceptionResponse.ConstraintViolationResponse.ConstraintViolationResponseBuilder;

@ControllerAdvice(annotations = RestController.class)
public class AdviceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handler(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse.from(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationResponse> handler(ConstraintViolationException exception) {

        ConstraintViolationResponseBuilder builder = ConstraintViolationResponse.builder();

        builder.operation(ExceptionResponse.from(exception));

        exception.getConstraintViolations().stream()
            .map(ExceptionResponse::from)
            .forEach(builder::detail);

        return ResponseEntity.status(BAD_REQUEST)
            .body(builder.build());
    }

}
