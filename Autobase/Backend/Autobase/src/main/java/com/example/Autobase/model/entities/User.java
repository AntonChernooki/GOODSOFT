package com.example.Autobase.model.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String login;
    private String password;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String login, String password) {
        this.roles = new HashSet<>();
        this.login = login;
        this.password = password;
        this.enabled = true;
    }

    public User(String login, Long id, String password, Set<Role> roles, Boolean enabled) {
        this.login = login;
        this.id = id;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }

    public boolean hasRole(String roleName) {
        if (roles == null) {
            return false;
        }
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }
}
