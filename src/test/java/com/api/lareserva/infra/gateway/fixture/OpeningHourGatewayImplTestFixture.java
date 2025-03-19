package com.api.lareserva.infra.gateway.fixture;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.infra.persistence.entity.OpeningHourEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import java.time.LocalTime;
import java.util.ArrayList;

public class OpeningHourGatewayImplTestFixture {

  public static OpeningHourEntity existingOpeningHourEntity() {
    return OpeningHourEntity.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.of(9, 0))
        .closeTime(LocalTime.of(18, 0))
        .restaurant(restaurantEntity())
        .build();
  }

  public static RestaurantEntity restaurantEntity() {
    return RestaurantEntity.builder()
        .id(1)
        .restaurantName("Test Restaurant")
        .cnpj("12345678901234")
        .address("123 Street")
        .city("Test City")
        .phoneNumber("1234567890")
        .typeOfFood("Italian")
        .capacity(50)
        .numberOfTables(10)
        .openingHours(new ArrayList<>())
        .email("test@gmail.com")
        .build();
  }

  public static OpeningHourEntity existingOpeningHourEntityWithoutRestaurant() {
    return OpeningHourEntity.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.of(9, 0))
        .closeTime(LocalTime.of(18, 0))
        .build();
  }

  public static OpeningHour updatedOpeningHour() {
    return OpeningHour.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.of(10, 0))
        .closeTime(LocalTime.of(19, 0))
        .build();
  }
}
