package com.example.Autobase.dao;

import com.example.Autobase.model.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(@Param("id") Long id);

    List<User> getAllUsers();

    Optional<User> getUserByLogin(@Param("login") String login);

    void updateUser(User user);

    void deleteUser(@Param("id") Long id);

    void deleteUserRoles(@Param("userId") Long userId);

    void addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void addUser(User user);
}