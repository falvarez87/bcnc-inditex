package com.inditex.infrastructure.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Represents a String To LocalDateTme Converter, in order to handle de datetime correctly.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

  private static final DateTimeFormatter[] FORMATTERS = {
    DateTimeFormatter.ISO_LOCAL_DATE_TIME,
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd")
  };

  @Override
  public LocalDateTime convert(String source) {
    if (source == null || source.trim().isEmpty()) {
      throw new IllegalArgumentException("Date parameter cannot be null or empty");
    }

    String trimmedSource = source.trim();

    for (DateTimeFormatter formatter : FORMATTERS) {
      try {
        if (formatter.equals(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) {
          return LocalDateTime.parse(
              trimmedSource + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return LocalDateTime.parse(trimmedSource, formatter);
      } catch (DateTimeParseException e) {
        continue;
      }
    }

    throw new IllegalArgumentException(
        "Invalid date format: '"
            + source
            + "'. Supported formats: yyyy-MM-ddTHH:mm:ss, yyyy-MM-ddTHH:mm:ss.SS,"
            + "yyyy-MM-ddTHH:mm, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd");
  }
}
