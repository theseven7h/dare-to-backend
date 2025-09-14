package com.dareTo.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlanRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 2, message = "Title must contain at least 2 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must contain at least 10 characters")
    private String description;
}