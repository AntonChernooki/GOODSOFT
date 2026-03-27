package com.example.Autobase.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;

        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException {
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


                if (isTokenValid) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.info("Authentication set with authorities:  " + userDetails.getAuthorities());
                } else {
                    logger.warn("JWT validation failed");
                }
            }


        }


        filterChain.doFilter(request, response);
    }
}
