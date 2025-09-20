package com.dareTo.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UpdatePlanRequest {
    @Size(min = 2, message = "Title must contain at least 2 characters")
    @Pattern(regexp = ".*\\S.*", message = "Title name must not be only whitespace")
    private String title;

    @Size(min = 10, message = "Description must contain at least 10 characters")
    @Pattern(regexp = ".*\\S.*", message = "Description name must not be only whitespace")
    private String description;

    @FutureOrPresent(message = "Start date cannot be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @FutureOrPresent(message = "Completion date cannot be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
