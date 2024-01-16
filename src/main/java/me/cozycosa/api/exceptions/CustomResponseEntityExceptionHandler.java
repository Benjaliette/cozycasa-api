package me.cozycosa.api.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraint(ConstraintViolationException ex,
                                                   WebRequest request) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        Map<String, String> messages = new HashMap<>(constraintViolations.size());

        constraintViolations.forEach(violation -> {
            messages.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), "Record validation error",
                request.getDescription(false), messages);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
