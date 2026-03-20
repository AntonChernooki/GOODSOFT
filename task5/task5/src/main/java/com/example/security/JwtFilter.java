package com.example.security;



import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("jwt filter");
        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization header:  " + authorizationHeader);
        String jwt;
        String login;
        List<String> roles;
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.info("JWT: " + jwt);
            login = jwtUtils.getLoginFromJwt(jwt);
            roles = jwtUtils.getRolesFromToken(jwt);
            logger.info("Login: , Roles:  " + login + " " + roles);

            if (Objects.nonNull(login) && Objects.nonNull(roles) && SecurityContextHolder.getContext().getAuthentication() == null) {
                boolean isTokenValid = jwtUtils.validateJwtToken(jwt);
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                if (isTokenValid) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, null, authorities);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.info("Authentication set with authorities:  "+ authorities);
                }
                else {
                    logger.warn("JWT validation failed");
                }
            }


        }


        filterChain.doFilter(request, response);
    }
}
