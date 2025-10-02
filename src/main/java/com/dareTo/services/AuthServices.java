package com.dareTo.services;

import com.dareTo.dto.requests.LoginRequest;
import com.dareTo.dto.requests.ResetRequest;
import com.dareTo.dto.requests.UserRequest;
import com.dareTo.dto.responses.LoginDto;
import com.dareTo.dto.responses.ResetPasswordResponse;
import com.dareTo.dto.responses.UserResponse;

public interface AuthServices {
    UserResponse registerUser(UserRequest userRequest);
    LoginDto loginUser(LoginRequest userRequest);
    ResetPasswordResponse forgotPassword(String email);
    boolean resetPassword(ResetRequest resetRequest);

}
