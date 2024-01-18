package me.cozycosa.api.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.apache.catalina.valves.JsonErrorReportValve;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(RecordNotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RecordNotAllowedException handleRecordNotAllowedException(RecordNotAllowedException e) {
        return e;
    }
}
