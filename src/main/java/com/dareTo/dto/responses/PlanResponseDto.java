package com.dareTo.dto.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PlanResponseDto extends BaseResponse {
    private PlanResponse planResponse;
    public PlanResponseDto(HttpStatus status, String message, PlanResponse planResponse) {
        super(status, message);
        this.planResponse = planResponse;
    }
}
