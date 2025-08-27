package com.inditex.domain.exception;

/**
 * Represents a PriceNotFoundException for controller.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
public class PriceNotFoundException extends RuntimeException {
  public PriceNotFoundException(String message) {
    super(message);
  }

  public PriceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
