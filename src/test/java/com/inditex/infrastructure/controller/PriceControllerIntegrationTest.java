package com.inditex.infrastructure.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.inditex.application.dto.PriceResponse;
import com.inditex.application.service.PriceService;
import com.inditex.domain.exception.PriceNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PriceController.class)
class PriceControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PriceService priceService;

  private static final Long PRODUCT_ID = 35455L;
  private static final Long BRAND_ID = 1L;
  private static final String CURRENCY = "EUR";

  @Test
  void test1_10amDay14_ShouldReturnPriceList1() throws Exception {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
    PriceResponse response =
        new PriceResponse(
            PRODUCT_ID,
            BRAND_ID,
            1,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            new BigDecimal("35.50"),
            CURRENCY);

    when(priceService.getApplicablePrice(any(), any(), any())).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.price").value(35.50))
        .andExpect(jsonPath("$.currency").value(CURRENCY))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"));
  }

  @Test
  void test2_4pmDay14_ShouldReturnPriceList2() throws Exception {
    // Given
    PriceResponse response =
        new PriceResponse(
            PRODUCT_ID,
            BRAND_ID,
            2,
            LocalDateTime.of(2020, 6, 14, 15, 0),
            LocalDateTime.of(2020, 6, 14, 18, 30),
            new BigDecimal("25.45"),
            CURRENCY);

    when(priceService.getApplicablePrice(any(), any(), any())).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2020-06-14T16:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  void test3_9pmDay14_ShouldReturnPriceList1() throws Exception {
    // Given
    PriceResponse response =
        new PriceResponse(
            PRODUCT_ID,
            BRAND_ID,
            1,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            new BigDecimal("35.50"),
            CURRENCY);

    when(priceService.getApplicablePrice(any(), any(), any())).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2020-06-14T21:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  void test4_10amDay15_ShouldReturnPriceList3() throws Exception {
    // Given
    PriceResponse response =
        new PriceResponse(
            PRODUCT_ID,
            BRAND_ID,
            3,
            LocalDateTime.of(2020, 6, 15, 0, 0),
            LocalDateTime.of(2020, 6, 15, 11, 0),
            new BigDecimal("30.50"),
            CURRENCY);

    when(priceService.getApplicablePrice(any(), any(), any())).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2020-06-15T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.priceList").value(3))
        .andExpect(jsonPath("$.price").value(30.50));
  }

  @Test
  void test5_9pmDay16_ShouldReturnPriceList4() throws Exception {
    // Given
    PriceResponse response =
        new PriceResponse(
            PRODUCT_ID,
            BRAND_ID,
            4,
            LocalDateTime.of(2020, 6, 15, 16, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            new BigDecimal("38.95"),
            CURRENCY);

    when(priceService.getApplicablePrice(any(), any(), any())).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2020-06-16T21:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.priceList").value(4))
        .andExpect(jsonPath("$.price").value(38.95));
  }

  @Test
  void shouldReturn404WhenPriceNotFound() throws Exception {
    // Given
    when(priceService.getApplicablePrice(any(), any(), any()))
        .thenThrow(new PriceNotFoundException("Price not found"));

    // When & Then
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2025-01-01T10:00:00")
                .param("productId", "99999")
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").exists());
  }

  @Test
  void shouldReturn400WhenInvalidDate() throws Exception {
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "invalid-date")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(containsString("Invalid date format")));
  }

  @Test
  void shouldReturn400WhenInvalidNumber() throws Exception {
    mockMvc
        .perform(
            get("/v1/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "not-a-number") // Número inválido
                .param("brandId", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(containsString("Invalid number format")));
  }

  @Test
  void shouldReturn400WhenMissingParameters() throws Exception {
    mockMvc
        .perform(get("/v1/prices").param("date", "2023-12-31T10:00:00"))
        .andExpect(status().isBadRequest());
  }
}
