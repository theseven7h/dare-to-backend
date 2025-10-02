package com.dareTo.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResetPasswordResponse {
    private String userId;
    private String token;
    private LocalDateTime expiryTime;
}
