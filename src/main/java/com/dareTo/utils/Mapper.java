package com.dareTo.utils;

import com.dareTo.data.models.Plan;
import com.dareTo.data.models.ResetToken;
import com.dareTo.data.models.User;
import com.dareTo.dto.requests.PlanRequest;
import com.dareTo.dto.requests.UserRequest;
import com.dareTo.dto.responses.LoginDto;
import com.dareTo.dto.responses.PlanResponse;
import com.dareTo.dto.responses.ResetPasswordResponse;
import com.dareTo.dto.responses.UserResponse;

import java.time.LocalDateTime;

import static com.dareTo.utils.Password.hashPassword;

public class Mapper {
    public static Plan mapToPlan(User user, PlanRequest requestDto) {
        Plan plan =  new Plan();
        plan.setUser(user);
        plan.setTitle(requestDto.getTitle());
        plan.setDescription(requestDto.getDescription());
        plan.setCompleted(false);
        plan.setStartDate(requestDto.getStartDate());
        plan.setEndDate(requestDto.getEndDate());
        plan.setLastModified(LocalDateTime.now());
        return plan;
    }

    public static PlanResponse mapToPlanResponse(Plan plan) {
        PlanResponse planResponse = new PlanResponse();
        planResponse.setId(plan.getId());
        planResponse.setTitle(plan.getTitle());
        planResponse.setDescription(plan.getDescription());
        planResponse.setCompleted(plan.isCompleted());
        planResponse.setStartDate(plan.getStartDate());
        planResponse.setEndDate(plan.getEndDate());
        planResponse.setLastModified(plan.getLastModified());
        return planResponse;
    }

    public static User mapToUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setEmail(request.getEmail());
        return user;
    }

    public static UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public static LoginDto mapToLoginDto(User user) {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(user.getUsername());
        loginDto.setEmail(user.getEmail());
        return loginDto;
    }

    public static ResetPasswordResponse mapToResetPasswordResponse(ResetToken resetToken) {
        ResetPasswordResponse response = new ResetPasswordResponse();
        response.setUserId(resetToken.getUserId());
        response.setToken(resetToken.getToken());
        response.setExpiryTime(resetToken.getExpiryTime());
        return response;
    }
}