package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.OpeningHourDto;
import java.time.LocalTime;
import java.util.List;

public class DeleteRestaurantTestFixture {
  private static final String RESTAURANT_NAME = "Doce Sonho";
  private static final String CNPJ = "12345678901234";
  private static final String ADDRESS = "Rua das Flores, n 100";
  private static final String CITY = "Natal";
  private static final String PHONE_NUMBER = "1234567890";
  private static final String TYPE_OF_FOOD = "Italian";
  private static final Integer CAPACITY = 50;
  private static final Integer NUMBER_OF_TABLES = 10;
  private static final String EMAIL = "test@gmail";

  private static OpeningHourDto validOpeningHourRequest() {
    return OpeningHourDto.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.parse("10:00"))
        .closeTime(LocalTime.parse("18:00"))
        .build();
  }

  public static Restaurant validRestaurantGatewayResponse() {
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
}
