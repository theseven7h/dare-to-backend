package com.dareTo.dto.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ListPlanResponseDto extends BaseResponse{
    private List<PlanResponse> plans;
    public ListPlanResponseDto(HttpStatus status, String message, List<PlanResponse> plans) {
        super(status, message);
        this.plans = plans;
    }
}
