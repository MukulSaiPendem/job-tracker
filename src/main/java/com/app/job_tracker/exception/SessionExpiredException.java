package com.app.job_tracker.exception;

import lombok.Getter;

@Getter
public class SessionExpiredException extends RuntimeException {
    private final String field;

    public SessionExpiredException(String field, String message) {
        super(message);
        this.field = field;
    }
}