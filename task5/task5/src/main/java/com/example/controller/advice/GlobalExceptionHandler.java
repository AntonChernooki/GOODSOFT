package com.example.controller.advice;

import com.example.dto.response.ErrorResponse;
import com.example.exeption.DatabaseException;
import com.example.exeption.UserAlreadyExistsException;
import com.example.exeption.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception){
        ErrorResponse error = new ErrorResponse("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("login", exception.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabase(DatabaseException exception) {
        System.out.println("exception = " + exception);
        ErrorResponse error = new ErrorResponse("error", "Ошибка базы данных");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
        System.out.println("exception = " + exception);
        ErrorResponse error = new ErrorResponse("error", "Ошибка сервера");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
