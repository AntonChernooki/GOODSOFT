package com.example.dao;

import com.example.model.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDao {
    Collection<User> getAllUsers() throws SQLException;

    User getUserByLogin(@Param("login") String login) throws SQLException;

    void addUser( User user) throws SQLException;

    void updateUser(@Param("oldLogin") String oldLogin,@Param("newUser") User newUser) throws SQLException;

    void deleteUser(@Param("login") String login) throws SQLException;
    void deleteUserRoles(@Param("login") String login) throws SQLException;
    void insertUserRole(@Param("login") String login,@Param("role") String role)throws SQLException;
}
