package com.dareTo.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetRequest {
    private String token;

    @NotBlank(message = "Email is required")
    @Email(message = "You entered an invalid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
