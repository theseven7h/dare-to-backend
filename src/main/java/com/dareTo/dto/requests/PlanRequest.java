package com.dareTo.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 2, message = "Title must contain at least 2 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must contain at least 10 characters")
    private String description;

    @FutureOrPresent(message = "Start date cannot be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @FutureOrPresent(message = "Completion date cannot be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}