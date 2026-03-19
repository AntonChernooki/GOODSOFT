package com.example.exeption;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Неверный логин или пароль");
    }
}
