package com.example.Autobase.exception;

public class TripOperationNotAllowedException extends RuntimeException {
    public TripOperationNotAllowedException(String message) {
        super(message);
    }
}