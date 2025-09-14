package com.dareTo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Plan {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDateTime lastModified;

    @DBRef
    private User user;
}
