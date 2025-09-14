package com.dareTo.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username is required")
    @Pattern(regexp = ".*[A-Za-z].*", message = "Username must contain at least one letter")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
