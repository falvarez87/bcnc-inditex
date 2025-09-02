package com.inditex.infrastructure.exception;

import com.inditex.domain.exception.PriceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Represents a GlobalExceptionHandler for controller.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /** Handles IllegalArgumentException (started from our Converter). */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** Handles MethodArgumentNotValidException for @Valid. */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    String errorMessage =
        ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));

    ErrorResponse errorResponse =
        new ErrorResponse("Validation error: " + errorMessage, LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** Handles MissingServletRequestParameterException. */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex) {

    String errorMessage = String.format("Missing required parameter: '%s'", ex.getParameterName());

    ErrorResponse errorResponse = new ErrorResponse(errorMessage, LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** Handles MethodArgumentTypeMismatchException. */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {

    String errorMessage;

    Class<?> requiredType = ex.getRequiredType();

    if (requiredType != null && requiredType.getName().contains("LocalDateTime")) {
      errorMessage =
          String.format(
              "Invalid date format '%s'. Expected format: yyyy-MM-ddTHH:mm:ss", ex.getValue());
    } else if (requiredType != null && (requiredType == Long.class || requiredType == long.class)) {
      errorMessage =
          String.format(
              "Invalid number format '%s' for parameter '%s'. Expected a valid number.",
              ex.getValue(), ex.getName());
    } else {
      errorMessage =
          String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());
    }

    ErrorResponse errorResponse = new ErrorResponse(errorMessage, LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** Handles ConstraintViolationException. */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    String errorMessage =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

    ErrorResponse errorResponse =
        new ErrorResponse("Validation error: " + errorMessage, LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** Handles PriceNotFoundException. */
  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePriceNotFound(PriceNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /** Handles all other exceptions. */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse errorResponse =
        new ErrorResponse("An unexpected error occurred", LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /** Handles BindException. */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
    String errorMessage =
        ex.getBindingResult().getAllErrors().stream()
            .map(
                error -> {
                  if (error instanceof org.springframework.validation.FieldError) {
                    org.springframework.validation.FieldError fieldError =
                        (org.springframework.validation.FieldError) error;

                    // Manejar específicamente errores de conversión
                    if (fieldError.isBindingFailure()) {
                      return String.format(
                          "Invalid value '%s' for parameter '%s'",
                          fieldError.getRejectedValue(), fieldError.getField());
                    }

                    return fieldError.getDefaultMessage();
                  }
                  return error.getDefaultMessage();
                })
            .collect(Collectors.joining("; "));

    ErrorResponse errorResponse =
        new ErrorResponse("Invalid request parameters: " + errorMessage, LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Represents a class for ErrorResponse.
   *
   * @author falvarez87
   * @version 1.0
   * @since 2025
   */
  @Getter
  public static class ErrorResponse {
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, LocalDateTime timestamp) {
      this.message = message;
      this.timestamp = timestamp;
    }
  }
}
