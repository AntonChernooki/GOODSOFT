/*package com.example.security;

import com.example.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;



public class CustomUserDetails extends User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    public CustomUserDetails(User user) {
        super(user.getLogin(),user.getPassword(), user.getEmail(), user.getSurname(), user.getName(), user.getPatronymic(), user.getBirthday(),user.getRoles());

    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
*/