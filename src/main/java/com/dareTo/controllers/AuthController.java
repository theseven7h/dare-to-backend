package com.dareTo.controllers;

import com.dareTo.config.JwtUtil;
import com.dareTo.dto.requests.LoginRequest;
import com.dareTo.dto.requests.UserRequest;
import com.dareTo.dto.responses.LoginDto;
import com.dareTo.dto.responses.LoginUserResponse;
import com.dareTo.dto.responses.RegisterUserResponse;
import com.dareTo.dto.responses.UserResponse;
import com.dareTo.services.impl.AuthServicesImpl;
import com.dareTo.utils.SessionManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthServicesImpl authServices;

    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = authServices.registerUser(userRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
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
}
