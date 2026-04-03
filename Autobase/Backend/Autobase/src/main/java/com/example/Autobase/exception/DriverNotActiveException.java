package com.example.Autobase.exception;

public class DriverNotActiveException extends RuntimeException {
    public DriverNotActiveException(String message) {
        super(message);
    }
}
