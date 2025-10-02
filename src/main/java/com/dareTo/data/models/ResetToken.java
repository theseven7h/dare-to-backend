package com.dareTo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class ResetToken {
    @Id
    private String token;
    private String userId;
    private LocalDateTime expiryTime;
}
