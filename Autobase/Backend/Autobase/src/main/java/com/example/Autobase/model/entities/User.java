package com.example.Autobase.model.entities;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String login;
    private String password;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Role> roles;

    public User(){
        this.roles=new HashSet<>();
    }

    public User(String login, String password) {
        this.roles=new HashSet<>();
        this.login = login;
        this.password = password;
        this.enabled = true;
    }


    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String roleName) {
        if (roles == null) {
            return false;
        }
        return roles.stream().anyMatch( role -> role.getName().equals(roleName));
    }
}
