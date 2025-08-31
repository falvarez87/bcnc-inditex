package com.inditex.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Represents a WebConfig to user the StringToLocalDateTimeConverter.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final StringToLocalDateTimeConverter stringToLocalDateTimeConverter;

  public WebConfig(StringToLocalDateTimeConverter stringToLocalDateTimeConverter) {
    this.stringToLocalDateTimeConverter = stringToLocalDateTimeConverter;
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(stringToLocalDateTimeConverter);
  }
}
