package com.example.Autobase.exception;

public class RepairRequestOperationNotAllowedException extends RuntimeException {
    public RepairRequestOperationNotAllowedException(String message) {
        super(message);
    }
}