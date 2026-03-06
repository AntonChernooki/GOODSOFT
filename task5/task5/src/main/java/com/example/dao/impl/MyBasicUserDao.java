package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.*;

@Repository
@Primary
public class MyBasicUserDao implements UserDao {

    private  final UserDao userDao;

    public MyBasicUserDao(UserDao userDao) {
        this.userDao = userDao;
        System.out.println("MyBasicUserDao: injected userDao class = " + userDao.getClass());

    }


    @Override
    public Collection<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();

    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
       return userDao.getUserByLogin(login);
    }

    @Override
    public void addUser(User user) throws SQLException {
        Set<Role> roles = user.getRoles();
        System.out.println("=== DAO addUser: roles before addUser = " + roles);
        userDao.addUser(user);
        System.out.println("=== DAO addUser: roles after addUser = " + user.getRoles());
        System.out.println("=== DAO addUser: saved roles = " + roles);
        if (roles != null) {
            for (Role role : roles) {
                System.out.println("=== DAO addUser: inserting role " + role.name());
                userDao.insertUserRole(user.getLogin(), role.name());
            }
        } else {
            System.out.println("=== DAO addUser: roles is null, skipping inserts");
        }
    }

    @Override
    public void updateUser(String oldLogin, User newUser) throws SQLException {
        Set<Role> roles = newUser.getRoles();
        System.out.println("=== DAO updateUser: deleting roles for oldLogin = " + oldLogin);
        userDao.deleteUserRoles(oldLogin);
        System.out.println("=== DAO updateUser: updating user");
        userDao.updateUser(oldLogin, newUser);
        System.out.println("=== DAO updateUser: roles after update = " + newUser.getRoles());
        System.out.println("=== DAO updateUser: saved roles = " + roles);
        if (roles != null) {
            for (Role role : roles) {
                System.out.println("=== DAO updateUser: inserting role " + role.name() + " for login " + newUser.getLogin());
                userDao.insertUserRole(newUser.getLogin(), role.name());
            }
        } else {
            System.out.println("=== DAO updateUser: roles is null, skipping inserts");
        }
    }

    @Override
    public void deleteUser(String login) throws SQLException {
        userDao.deleteUserRoles(login);
        userDao.deleteUser(login);
    }

    @Override
    public void deleteUserRoles(String login) throws SQLException {
        userDao.deleteUserRoles(login);
    }

    @Override
    public void insertUserRole(String login, String role) throws SQLException {
        userDao.insertUserRole(login, role);
    }



}