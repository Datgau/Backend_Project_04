package com.example.project_backend04.dto.response.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String role;
    private JwtResponse token;
}
