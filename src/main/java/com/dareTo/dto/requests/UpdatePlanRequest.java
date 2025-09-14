package com.dareTo.dto.requests;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePlanRequest {
    @Size(min = 2, message = "Title must contain at least 2 characters")
    @Pattern(regexp = ".*\\S.*", message = "Title name must not be only whitespace")
    private String title;

    @Size(min = 10, message = "Description must contain at least 10 characters")
    @Pattern(regexp = ".*\\S.*", message = "Description name must not be only whitespace")
    private String description;
}
