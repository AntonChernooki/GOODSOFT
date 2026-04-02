package com.example.Autobase.security;


import com.example.Autobase.dao.UserDao;
import com.example.Autobase.exception.UserNotFoundException;
import com.example.Autobase.model.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> loadUserByUsername: username = " + username);
        System.out.println("Loading user: " + username);
        User user = userDao.getUserByLogin(username).orElseThrow(()->new UserNotFoundException("не получилось найти пользователя по логину"));
        System.out.println("User from DB: login=" + user.getLogin() + ", password=" + user.getPassword());
        System.out.println(">>> user from DAO = " + user);
        System.out.println(">>> user roles = " + user.getRoles());
        return new CustomUserDetails(user);
    }
}
