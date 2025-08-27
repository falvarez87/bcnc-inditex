package com.inditex.infrastructure.exception;

import com.inditex.domain.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<Object> handlePriceNotFoundException(
      PriceNotFoundException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());
    body.put("status", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "An unexpected error occurred");
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
