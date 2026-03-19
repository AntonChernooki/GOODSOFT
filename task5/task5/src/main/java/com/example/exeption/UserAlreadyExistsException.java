package com.example.exeption;

public class UserAlreadyExistsException extends RuntimeException{
    public  UserAlreadyExistsException(String login){
        super("такой пользователь уже зарегистрироавн"+ login);
    }
}
