package com.example.Autobase.exception;

public class DuplicateRepairRequestException extends RuntimeException {
    public DuplicateRepairRequestException(String message) {
        super(message);
    }
}
