package me.cozycosa.api.exceptions;

import org.apache.catalina.valves.JsonErrorReportValve;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(RecordNotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RecordNotAllowedException handleRecordNotAllowedException(RecordNotAllowedException e) {
        return e;
    }
}
