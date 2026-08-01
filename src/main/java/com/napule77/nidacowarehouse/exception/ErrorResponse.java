package com.napule77.nidacowarehouse.exception;

import java.time.Instant;

/**
 * Standard JSON error body returned by {@link GlobalExceptionHandler}.
 */
public record ErrorResponse(int status, String error, String message, Instant timestamp) {

    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(status, error, message, Instant.now());
    }
}
