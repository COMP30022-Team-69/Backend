package com.team69.itproject.exceptions;

import com.team69.itproject.entities.ResponseEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e) {
        String message = "Error code: " + e.getCode() + " Details: " + e.getLocalizedMessage();
        log.error(message);
        return ResponseEntity.error(e.getCode(), message);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error("Not Found: {}", e.getLocalizedMessage());
        String message = e.getMessage();
        return ResponseEntity.error(404, message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access Denied: {}", e.getLocalizedMessage());
        String message = e.getMessage();
        return ResponseEntity.error(403, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Bad request: {}", e.getLocalizedMessage());
        String message = e.getMessage();
        return ResponseEntity.error(400, message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        log.error("Bad request: {}", e.getLocalizedMessage());
        String message = e.getMessage();
        return ResponseEntity.error(400, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error(e);
        String message = e.getMessage();
        return ResponseEntity.error(500, message);
    }
}
