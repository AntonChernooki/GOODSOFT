package com.example.Autobase.dao;

import com.example.Autobase.model.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {

    Optional<Role> getRoleById(Long id);

    Optional<Role> getRoleByName(String name);

    List<Role> getAllRoles();

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);


}
