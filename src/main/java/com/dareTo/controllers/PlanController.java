package com.dareTo.controllers;

import com.dareTo.dto.requests.PlanRequest;
import com.dareTo.dto.responses.ListPlanResponseDto;
import com.dareTo.dto.responses.PlanResponseDto;
import com.dareTo.dto.responses.PlanResponse;
import com.dareTo.services.impl.PlanServicesImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@Slf4j
public class PlanController {
    @Autowired
    private PlanServicesImpl planServices;

    @PostMapping("/addPlan")
    public ResponseEntity<PlanResponseDto> addPlan(@Valid @RequestBody PlanRequest planRequest, HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        PlanResponse planResponse = planServices.createPlan(userId, planRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new PlanResponseDto(
                                HttpStatus.CREATED,
                                "Plan created successfully",
                                planResponse
                        )
                );
    }

    @GetMapping("")
    public ResponseEntity<ListPlanResponseDto> getAllPlans(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        List<PlanResponse> plans = planServices.getAllPlans(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ListPlanResponseDto(
                                HttpStatus.OK,
                                "Plans gotten successfully",
                                plans
                        )
                );
    }

    @PatchMapping("/{id}/mark")
    public ResponseEntity<PlanResponseDto> markAsCompleteOrIncomplete(@PathVariable String id, HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        PlanResponse response = planServices.markPlanAsCompleteOrIncomplete(userId, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new PlanResponseDto(
                                HttpStatus.OK,
                                response.isCompleted() ? "Plan marked as complete" : "Plan marked as incomplete",
                                response
                        )
                );
    }

    @GetMapping("/completed")
    public ResponseEntity<ListPlanResponseDto> getAllCompletedPlans(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        List<PlanResponse> completedPlans = planServices.getCompletedPlans(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ListPlanResponseDto(
                                HttpStatus.OK,
                                "Plans retrieved successfully",
                                completedPlans
                        )
                );
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<ListPlanResponseDto> getAllUnCompletedPlans(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        List<PlanResponse> uncompletedPlans = planServices.getUncompletedPlans(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ListPlanResponseDto(
                                HttpStatus.OK,
                                "Plans retrieved successfully",
                                uncompletedPlans
                        )
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePlan(@PathVariable String id, HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        log.info("User: {}", userId);
        System.out.println(userId);
        boolean isDeleted = planServices.deletePlanById(userId, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(isDeleted);
    }

//    @PatchMapping("/{id}/incomplete")
//    public ResponseEntity<PlanResponseDto> markAsIncomplete(@PathVariable String id, HttpServletRequest request) {
//        String userId = request.getAttribute("userId").toString();
//        PlanResponse response = planServices.markPlanAsIncomplete(userId, id);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(
//                        new PlanResponseDto(
//                                HttpStatus.OK,
//                                "Plan marked as incomplete",
//                                response
//                        )
//                );
//    }
}
