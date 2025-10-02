package com.dareTo.services.impl;

import com.dareTo.config.JwtManager;
import com.dareTo.data.models.ResetToken;
import com.dareTo.data.models.User;
import com.dareTo.data.repositories.ResetTokenRepo;
import com.dareTo.data.repositories.UserRepo;
import com.dareTo.dto.requests.LoginRequest;
import com.dareTo.dto.requests.ResetRequest;
import com.dareTo.dto.requests.UserRequest;
import com.dareTo.dto.responses.LoginDto;
import com.dareTo.dto.responses.ResetPasswordResponse;
import com.dareTo.dto.responses.UserResponse;
import com.dareTo.exceptions.*;
import com.dareTo.services.AuthServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dareTo.utils.Mapper.*;
import static com.dareTo.utils.Password.hashPassword;
import static com.dareTo.utils.Password.matches;

@Slf4j
@Service
public class AuthServicesImpl implements AuthServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ResetTokenRepo resetTokenRepo;

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private EmailTypeServices  emailTypeServices;


//    @Autowired
//    private SessionManager sessionManager;

    @Override
    public UserResponse registerUser(UserRequest request) {
        if(userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameTakenException("The username " + request.getUsername() + " already exists");
        }
        if(userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailTakenException("Email already in use");
        }
        User user = mapToUser(request);
        User savedUser = userRepo.save(user);

        emailTypeServices.sendWelcomeEmail(request);
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

        String token = jwtManager.generateToken(user.getId(), user.getUsername());

        LoginDto response = mapToLoginDto(user);
        response.setToken(token);
        return response;
    }

    @Override
    public ResetPasswordResponse forgotPassword(String email) {
        log.info("Forgot password reset request");
        User user = userRepo.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("No user with this email"));

        ResetToken resetToken = resetTokenRepo.findByUserId(user.getId());
        if(resetToken != null) {
            resetTokenRepo.delete(resetToken);
        }
        ResetToken newResetToken = getResetToken(user);
        ResetToken savedResetToken = resetTokenRepo.save(newResetToken);

        emailTypeServices.sendForgotPasswordEmail(email, newResetToken.getToken());
        return mapToResetPasswordResponse(savedResetToken);
    }

    @Override
    public boolean resetPassword(ResetRequest resetRequest) {
        User user = userRepo.findByEmail(resetRequest.getEmail()).orElseThrow(() -> new EmailNotFoundException("No user with this email"));
        ResetToken resetToken = resetTokenRepo.findByUserId(user.getId());
        if(resetToken == null || resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            resetTokenRepo.delete(resetToken);
            throw new InvalidTokenException("Token is invalid or expired");
        }
        user.setPassword(hashPassword(resetRequest.getPassword()));
        userRepo.save(user);
        resetTokenRepo.delete(resetToken);
        return true;
    }


    private ResetToken getResetToken(User user) {
        UUID token = UUID.randomUUID();
        ResetToken resetToken = new ResetToken();
        resetToken.setUserId(user.getId());
        resetToken.setToken(token.toString());
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));
        return resetToken;
    }

}
