package com.example.Autobase.dao;

import com.example.Autobase.model.entities.Role;
import com.example.Autobase.model.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById( Long id);

    List<User> getAllUsers();

    Optional<User> getUserByLogin( String login);

    List<Role> getRolesByUserId( Long userId);

    void updateUser(User user);

    void deleteUser( Long id);

    void deleteUserRoles( Long userId);

    void addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void addUser(User user);
}