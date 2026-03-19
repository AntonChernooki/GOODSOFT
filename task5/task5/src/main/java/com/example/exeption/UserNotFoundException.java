package com.example.exeption;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String login) {
        super("Не нашлось пользователя с логином: " + login);
    }
}
