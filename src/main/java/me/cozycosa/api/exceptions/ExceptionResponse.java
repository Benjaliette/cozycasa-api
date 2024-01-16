package me.cozycosa.api.exceptions;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public record ExceptionResponse(LocalDateTime timestamp,
                                String message,
                                String details,
                                Map<String, String> messages) {

}
