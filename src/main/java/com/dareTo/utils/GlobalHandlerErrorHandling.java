package com.dareTo.utils;

import com.dareTo.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerErrorHandling {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put("field",fieldError.getField());
            errors.put("message",fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Map<String, String>> handleUsernameTakenException(UsernameTakenException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "username taken");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleServletException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "Unauthorized");
        errors.put("message", "Please login again");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "user not found");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(EmptyUpdateDetailsException.class)
    public ResponseEntity<Map<String, String>> handleEmptyUpdateDetailsException(EmptyUpdateDetailsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "empty update");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectUsernameOrPasswordException(IncorrectUsernameOrPasswordException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "username,password");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, String>> handleDateTimeParseException(DateTimeParseException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "dateTime");
        errors.put("message", "Date format is invalid (use yyyy-MM-dd)");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePlanNotFoundException(PlanNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "plan");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<Map<String, String>> handleEmailTakenException(EmailTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new HashMap<>(Map.of("field", "email taken", "message", ex.getMessage())));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Map<String, String>> handleJsonProcessingException(JsonProcessingException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "json processing error");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}