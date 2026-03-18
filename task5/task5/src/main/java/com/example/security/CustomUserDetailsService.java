/*package com.example.security;

import com.example.dao.UserDao;
import com.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> loadUserByUsername: username = " + username);
        try {
            System.out.println("Loading user: " + username);
            User user = userDao.getUserByLogin(username);
            System.out.println("User from DB: login=" + user.getLogin() + ", password=" + user.getPassword());
            System.out.println(">>> user from DAO = " + user);
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            System.out.println(">>> user roles = " + user.getRoles());
            return new CustomUserDetails(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
*/