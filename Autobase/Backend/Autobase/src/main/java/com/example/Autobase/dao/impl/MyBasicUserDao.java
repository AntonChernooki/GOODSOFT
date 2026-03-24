package com.example.Autobase.dao.impl;

import com.example.Autobase.dao.UserDao;
import com.example.Autobase.model.entities.Role;
import com.example.Autobase.model.entities.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MyBasicUserDao implements UserDao {
    private final UserDao userDao;

    public MyBasicUserDao(UserDao userDao) {

        this.userDao = userDao;
        System.out.println("MyBasicUserDao: userDao class = " + userDao.getClass());


    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        return userDao.getRolesByUserId(userId);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);

    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public void deleteUserRoles(Long userId) {

    }

    @Override
    public void addUserRole(Long userId, Long roleId) {

    }

    @Override
    public void addUser(User user) {

    }


}
