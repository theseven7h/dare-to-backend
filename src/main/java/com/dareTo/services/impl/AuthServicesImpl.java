package com.dareTo.services.impl;

import com.dareTo.config.JwtUtil;
import com.dareTo.data.models.User;
import com.dareTo.data.repositories.UserRepo;
import com.dareTo.dto.requests.LoginRequest;
import com.dareTo.dto.requests.UserRequest;
import com.dareTo.dto.responses.LoginDto;
import com.dareTo.dto.responses.UserResponse;
import com.dareTo.exceptions.IncorrectUsernameOrPasswordException;
import com.dareTo.exceptions.UserNotFoundException;
import com.dareTo.exceptions.UsernameTakenException;
import com.dareTo.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dareTo.utils.Mapper.*;
import static com.dareTo.utils.Password.matches;

@Service
public class AuthServicesImpl implements AuthServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private SessionManager sessionManager;

    @Override
    public UserResponse registerUser(UserRequest request) {
        if(userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameTakenException("The username " + request.getUsername() + " already exists");
        }
        User user = mapToUser(request);
        User savedUser = userRepo.save(user);
        return mapToUserResponse(savedUser);
    }

    @Override
    public LoginDto loginUser(LoginRequest userRequest) {
        User user = userRepo.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new IncorrectUsernameOrPasswordException(
                        "Username or password is incorrect"
                ));
        if(!matches(userRequest.getPassword(), user.getPassword())) {
            throw new IncorrectUsernameOrPasswordException("Username or password is incorrect");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        LoginDto response = mapToLoginDto(user);
        response.setToken(token);
        return response;
    }
}
