package com.app.job_tracker.exception;

import lombok.Getter;

@Getter
public class IncorrectLoginDetailsException extends RuntimeException {
    private final String field;

    public IncorrectLoginDetailsException(String field, String message) {
        super(message);
        this.field = field;
    }
}
