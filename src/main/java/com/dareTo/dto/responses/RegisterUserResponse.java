package com.dareTo.dto.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RegisterUserResponse extends BaseResponse {
    private UserResponse userResponse;
    public RegisterUserResponse(HttpStatus status, String message, UserResponse userResponse) {
        super(status, message);
        this.userResponse = userResponse;
    }
}
