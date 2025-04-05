package org.spring.geeksphere.exception;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Request");
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<ProblemDetail> handleMongoWriteException(MongoWriteException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Database write error occurred");
        problem.setTitle("Database Error");
        log.error("MongoDB write error: ", ex);
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ProblemDetail> handleDuplicateKeyException(DuplicateKeyException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, "Resource already exists");
        problem.setTitle("Duplicate Entry");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<ProblemDetail> handleMongoException(MongoException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurred");
        problem.setTitle("Database Error");
        log.error("MongoDB error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Validation Error");
        problem.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problem);
    }

@ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, ex.getMessage());
    problem.setTitle("Resource Not Found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
}

@ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.FORBIDDEN, "Access denied");
    problem.setTitle("Forbidden");
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    problem.setTitle("Internal Server Error");
    log.error("Unexpected error: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
}
}
