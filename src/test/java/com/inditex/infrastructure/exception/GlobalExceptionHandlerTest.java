package com.inditex.infrastructure.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.inditex.domain.exception.PriceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

  @Test
  void handlePriceNotFound_ReturnsNotFoundResponse() {
    // Given
    PriceNotFoundException ex = new PriceNotFoundException("Price not found");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handlePriceNotFound(ex);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Price not found", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleMethodArgumentTypeMismatch_WithLocalDateTime_ReturnsBadRequestWithDateMessage() {
    // Given
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);

    // ⚠️ MOCK COMPLETO de todas las propiedades necesarias
    when(ex.getRequiredType()).thenReturn((Class) LocalDateTime.class);
    when(ex.getValue()).thenReturn("invalid-date");
    when(ex.getName()).thenReturn("date");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleMethodArgumentTypeMismatch(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("Invalid date format"));
    assertTrue(response.getBody().getMessage().contains("invalid-date"));
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleMethodArgumentTypeMismatch_WithLong_ReturnsBadRequestWithNumberMessage() {
    // Given
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);

    // ⚠️ MOCK COMPLETO para tipo Long
    when(ex.getRequiredType()).thenReturn((Class) Long.class);
    when(ex.getValue()).thenReturn("not-a-number");
    when(ex.getName()).thenReturn("productId");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleMethodArgumentTypeMismatch(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("Invalid number format"));
    assertTrue(response.getBody().getMessage().contains("not-a-number"));
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleMethodArgumentTypeMismatch_WithOtherType_ReturnsBadRequestWithGenericMessage() {
    // Given
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);

    // ⚠️ MOCK para otro tipo
    when(ex.getRequiredType()).thenReturn((Class) Integer.class);
    when(ex.getValue()).thenReturn("invalid-value");
    when(ex.getName()).thenReturn("someParam");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleMethodArgumentTypeMismatch(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("Invalid value"));
    assertTrue(response.getBody().getMessage().contains("invalid-value"));
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleMethodArgumentTypeMismatch_WithNullRequiredType_ReturnsBadRequestWithGenericMessage() {
    // Given
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);

    // ⚠️ MOCK con requiredType null
    when(ex.getRequiredType()).thenReturn(null);
    when(ex.getValue()).thenReturn("some-value");
    when(ex.getName()).thenReturn("someParam");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleMethodArgumentTypeMismatch(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("Invalid value"));
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleIllegalArgumentException_ReturnsBadRequest() {
    // Given
    IllegalArgumentException ex = new IllegalArgumentException("Invalid date format");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleIllegalArgumentException(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Invalid date format", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleGenericException_ReturnsInternalServerError() {
    // Given
    Exception ex = new Exception("Some unexpected error");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleGenericException(ex);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void handleBindException_WithBindingFailure_ReturnsBadRequest() {
    // Given
    BindException ex = mock(BindException.class);
    when(ex.getBindingResult()).thenReturn(mock(BindingResult.class));
    when(ex.getBindingResult().getAllErrors()).thenReturn(List.of());
    when(ex.getBindingResult().getFieldErrors()).thenReturn(List.of());

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleBindException(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("Invalid request parameters"));
  }

  @Test
  void handleMethodArgumentNotValid_WithFieldErrors_ReturnsBadRequest() {
    // Given
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    FieldError fieldError = new FieldError("object", "field", "default message");
    when(ex.getBindingResult()).thenReturn(mock(BindingResult.class));
    when(ex.getBindingResult().getFieldErrors()).thenReturn(List.of(fieldError));

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleMethodArgumentNotValid(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("field: default message"));
  }

  @Test
  void handleConstraintViolation_WithViolations_ReturnsBadRequest() {
    // Given
    ConstraintViolationException ex = mock(ConstraintViolationException.class);
    ConstraintViolation<?> violation = mock(ConstraintViolation.class);
    when(violation.getMessage()).thenReturn("must not be null");
    when(ex.getConstraintViolations()).thenReturn(Set.of(violation));

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleConstraintViolation(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("must not be null"));
  }

  @Test
  void handleMissingServletRequestParameter_ReturnsBadRequest() {
    // Given
    MissingServletRequestParameterException ex =
        new MissingServletRequestParameterException("paramName", "paramType");

    // When
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        exceptionHandler.handleMissingServletRequestParameter(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getMessage().contains("Missing required parameter"));
  }
}
