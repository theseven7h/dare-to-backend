package com.dareTo.controllers;

import com.dareTo.dto.requests.ForgotPasswordRequest;
import com.dareTo.dto.requests.LoginRequest;
import com.dareTo.dto.requests.ResetRequest;
import com.dareTo.dto.requests.UserRequest;
import com.dareTo.dto.responses.*;
import com.dareTo.services.impl.AuthServicesImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthServicesImpl authServices;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) throws JsonProcessingException {
        UserResponse userResponse = authServices.registerUser(userRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new RegisterUserResponse(
                                HttpStatus.CREATED,
                                "User created successfully",
                                userResponse
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginDto loginResponse = authServices.loginUser(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION,
                "Bearer " + loginResponse.getToken())
                .body(
                    new LoginUserResponse(
                            HttpStatus.OK,
                            "Login successful",
                            new UserResponse(
                                    loginResponse.getUsername(),
                                    loginResponse.getEmail()
                            )
                    )
                );
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ResetPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        ResetPasswordResponse response = authServices.forgotPassword(request.getEmail());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody ResetRequest resetRequest) {
        Boolean response = authServices.resetPassword(resetRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
