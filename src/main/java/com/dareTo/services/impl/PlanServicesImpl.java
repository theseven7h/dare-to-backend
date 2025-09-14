package com.dareTo.services.impl;

import com.dareTo.data.models.Plan;
import com.dareTo.data.models.User;
import com.dareTo.data.repositories.PlanRepo;
import com.dareTo.data.repositories.UserRepo;
import com.dareTo.dto.requests.PlanRequest;
import com.dareTo.dto.requests.UpdatePlanRequest;
import com.dareTo.dto.responses.PlanResponse;
import com.dareTo.exceptions.EmptyUpdateDetailsException;
import com.dareTo.exceptions.PlanNotFoundException;
import com.dareTo.exceptions.UserNotFoundException;
import com.dareTo.services.PlanServices;
import com.dareTo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.dareTo.utils.Mapper.mapToPlan;
import static com.dareTo.utils.Mapper.mapToPlanResponse;

@Service
public class PlanServicesImpl implements PlanServices {

    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public PlanResponse createPlan(String userId, PlanRequest requestDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
        Plan plan = mapToPlan(user, requestDto);
        Plan savedPlan = planRepo.save(plan);
        return mapToPlanResponse(savedPlan);
    }

    @Override
    public List<PlanResponse> getAllPlans(String userId) {
        return planRepo.findAll()
                .stream()
                .filter(plan -> plan.getUser().getId().equals(userId))
                .map(Mapper::mapToPlanResponse)
                .toList();
    }

    @Override
    public PlanResponse getPlanById(String userId, String id) {
        User user =  userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Plan plan = planRepo.findById(id).orElseThrow(
                () -> new PlanNotFoundException("Plan with this ID not found")
        );
        return mapToPlanResponse(plan);
    }

    @Override
    public PlanResponse markPlanAsCompleteOrIncomplete(String userId, String id) {
        User user =  userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Plan plan = planRepo.findById(id).orElseThrow(
                () -> new PlanNotFoundException("Plan with this ID not found")
        );
        plan.setCompleted(!plan.isCompleted());
        plan.setLastModified(LocalDateTime.now());
        Plan savedPlan = planRepo.save(plan);
        return mapToPlanResponse(savedPlan);
    }

//    @Override
//    public PlanResponse markPlanAsIncomplete(String userId, String id) {
//        Plan plan = planRepo.findById(id).orElseThrow(
//                () -> new PlanNotFoundException("Plan with this ID not found")
//        );
//        plan.setCompleted(false);
//        plan.setLastModified(LocalDateTime.now());
//        Plan savedPlan = planRepo.save(plan);
//        return mapToPlanResponse(savedPlan);
//    }

    @Override
    public boolean deletePlanById(String userId, String id) {
        User user =  userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Plan plan = planRepo.findById(id).orElseThrow(
                () -> new PlanNotFoundException("Plan with this ID not found")
        );
        planRepo.delete(plan);
        return true;
    }

    @Override
    public PlanResponse updatePlan(String userId, String id, UpdatePlanRequest requestDto) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Plan plan = planRepo.findById(id).orElseThrow(
                () -> new PlanNotFoundException("Plan with this ID not found")
        );

        boolean titleIsPresent = requestDto.getTitle() != null && !requestDto.getTitle().isEmpty();
        boolean descriptionIsPresent = requestDto.getDescription() != null && !requestDto.getDescription().isEmpty();

        if(!titleIsPresent && !descriptionIsPresent) {
            throw new EmptyUpdateDetailsException("At least title or description is required");
        }
        if(titleIsPresent) {
            plan.setTitle(requestDto.getTitle());
        }
        if(descriptionIsPresent) {
            plan.setDescription(requestDto.getDescription());
        }
        Plan UpdatedPlan = planRepo.save(plan);
        return mapToPlanResponse(UpdatedPlan);
    }

    @Override
    public List<PlanResponse> getCompletedPlans(String userId) {
        return planRepo.findAll()
                .stream()
                .filter(Plan::isCompleted)
                .filter(plan -> plan.getUser().getId().equals(userId))
                .map(Mapper::mapToPlanResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> getUncompletedPlans(String userId) {
        return planRepo.findAll()
                .stream()
                .filter(plan -> !plan.isCompleted())
                .filter(plan -> plan.getUser().getId().equals(userId))
                .map(Mapper::mapToPlanResponse)
                .toList();
    }
}
