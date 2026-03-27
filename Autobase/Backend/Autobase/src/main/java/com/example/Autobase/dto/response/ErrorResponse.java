package com.example.Autobase.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors;
    private LocalDateTime timestamp;


    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int status, String error, Map<String, String> validationErrors) {
        this.status = status;
        this.error = error;
        this.validationErrors = validationErrors;
    }
}
