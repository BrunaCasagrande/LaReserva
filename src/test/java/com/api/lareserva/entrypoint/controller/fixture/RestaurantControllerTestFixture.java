package com.api.lareserva.entrypoint.controller.fixture;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.OpeningHourDto;
import com.api.lareserva.core.dto.UpdateRestaurantDto;
import com.api.lareserva.presenter.response.OpeningHourPresenterResponse;
import com.api.lareserva.presenter.response.RestaurantPresenterResponse;
import java.time.LocalTime;
import java.util.List;

public class RestaurantControllerTestFixture {
  private static final String RESTAURANT_NAME = "Doce Sonho";
  private static final String CNPJ = "12345678901234";
  private static final String ADDRESS = "Rua das Flores, n 100";
  private static final String CITY = "Natal";
  private static final String PHONE_NUMBER = "1234567890";
  private static final String TYPE_OF_FOOD = "Italian";
  private static final Integer CAPACITY = 50;
  private static final Integer NUMBER_OF_TABLES = 10;
  private static final String EMAIL = "test@gmail";
  private static final String PASSWORD = "123aAd!12";
  private static final String NEW_ADDRESS = "Rua dos Campos, n 200";
  private static final String NEW_PHONE_NUMBER = "9876543210";
  private static final String NEW_TYPE_OF_FOOD = "Japanese";
  private static final Integer NEW_CAPACITY = 100;
  private static final Integer NEW_NUMBER_OF_TABLES = 20;
  private static final String NEW_EMAIL = "updated@gmail.com";
  private static final String NEW_PASSWORD = "newSecurePassword123!";

  private static OpeningHourDto validOpeningHourRequest() {
    return OpeningHourDto.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.parse("10:00"))
        .closeTime(LocalTime.parse("18:00"))
        .build();
  }

  public static Restaurant validRestaurantRequest() {
    return Restaurant.builder()
        .restaurantName(RESTAURANT_NAME)
        .cnpj(CNPJ)
        .address(ADDRESS)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .typeOfFood(TYPE_OF_FOOD)
        .capacity(CAPACITY)
        .numberOfTables(NUMBER_OF_TABLES)
        .openingHour(List.of(validOpeningHourRequest()))
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static Restaurant validRestaurantResponse() {
    return Restaurant.builder()
        .id(1)
        .restaurantName(RESTAURANT_NAME)
        .cnpj(CNPJ)
        .address(ADDRESS)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .typeOfFood(TYPE_OF_FOOD)
        .capacity(CAPACITY)
        .numberOfTables(NUMBER_OF_TABLES)
        .openingHour(List.of(validOpeningHourRequest()))
        .email(EMAIL)
        .build();
  }

  public static OpeningHourPresenterResponse validOpeningHourPresenterResponse() {
    return OpeningHourPresenterResponse.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.parse("10:00"))
        .closeTime(LocalTime.parse("18:00"))
        .build();
  }

  public static RestaurantPresenterResponse validRestaurantPresenterResponse() {
    return RestaurantPresenterResponse.builder()
        .id(1)
        .restaurantName(RESTAURANT_NAME)
        .cnpj(CNPJ)
        .address(ADDRESS)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .typeOfFood(TYPE_OF_FOOD)
        .capacity(CAPACITY)
        .numberOfTables(NUMBER_OF_TABLES)
        .openingHours(List.of(validOpeningHourPresenterResponse()))
        .email(EMAIL)
        .build();
  }

  public static UpdateRestaurantDto validUpdateRestaurantRequest() {
    return UpdateRestaurantDto.builder()
        .address(NEW_ADDRESS)
        .phoneNumber(NEW_PHONE_NUMBER)
        .typeOfFood(NEW_TYPE_OF_FOOD)
        .capacity(NEW_CAPACITY)
        .numberOfTables(NEW_NUMBER_OF_TABLES)
        .email(NEW_EMAIL)
        .password(NEW_PASSWORD)
        .build();
  }

  public static Restaurant validUpdateRestaurantResponse() {
    return Restaurant.builder()
        .id(1)
        .restaurantName(RESTAURANT_NAME)
        .cnpj(CNPJ)
        .address(NEW_ADDRESS)
        .city(CITY)
        .phoneNumber(NEW_PHONE_NUMBER)
        .typeOfFood(NEW_TYPE_OF_FOOD)
        .capacity(NEW_CAPACITY)
        .numberOfTables(NEW_NUMBER_OF_TABLES)
        .openingHour(List.of(validOpeningHourRequest()))
        .email(NEW_EMAIL)
        .password(NEW_PASSWORD)
        .build();
  }

  public static RestaurantPresenterResponse validUpdateRestaurantPresenterResponse() {
    return RestaurantPresenterResponse.builder()
        .id(1)
        .restaurantName(RESTAURANT_NAME)
        .cnpj(CNPJ)
        .address(NEW_ADDRESS)
        .city(CITY)
        .phoneNumber(NEW_PHONE_NUMBER)
        .typeOfFood(NEW_TYPE_OF_FOOD)
        .capacity(NEW_CAPACITY)
        .numberOfTables(NEW_NUMBER_OF_TABLES)
        .openingHours(List.of(validOpeningHourPresenterResponse()))
        .email(NEW_EMAIL)
        .build();
  }
}
