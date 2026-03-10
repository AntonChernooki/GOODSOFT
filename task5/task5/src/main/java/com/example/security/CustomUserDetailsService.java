package com.example.security;

import com.example.dao.UserDao;
import com.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;

public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDao.getUserByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("Пользователь не найден: " + username);
            }
            return new CustomUserDetails(user);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка базы данных", e);
        }
    }
}
