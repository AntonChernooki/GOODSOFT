package com.example.dto.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String login;

    public JwtResponse(String token, String login) {
        this.token = token;
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }
}
