package com.dareTo.dto.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LoginUserResponse extends BaseResponse {
    private UserResponse user;
    public LoginUserResponse(HttpStatus status, String message, UserResponse user) {
        super(status, message);
        this.user = user;
    }
}
