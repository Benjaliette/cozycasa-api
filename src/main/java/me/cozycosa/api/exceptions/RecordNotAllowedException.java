package me.cozycosa.api.exceptions;

import lombok.*;

import java.util.NoSuchElementException;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RecordNotAllowedException extends NoSuchElementException {
    private int errorCode;
    private String errorMessage;
}