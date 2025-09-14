package com.dareTo.services;

import com.dareTo.dto.requests.PlanRequest;
import com.dareTo.dto.requests.UpdatePlanRequest;
import com.dareTo.dto.responses.PlanResponse;

import java.util.List;

public interface PlanServices {
    PlanResponse createPlan(String id, PlanRequest requestDto);
    List<PlanResponse> getAllPlans(String userId);
    PlanResponse getPlanById(String userId, String id);
    PlanResponse markPlanAsCompleteOrIncomplete(String userId, String id);
    boolean deletePlanById(String userId, String id);
    PlanResponse updatePlan(String userId, String id, UpdatePlanRequest requestDto);
    List<PlanResponse> getCompletedPlans(String userId);
    List<PlanResponse> getUncompletedPlans(String userId);
}
