package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import java.time.LocalTime;
import java.util.Date;

public class DeleteReservationTestFixture {

  public static final Integer EXISTING_RESERVATION_ID = 1;

  private static final Date RESERVATION_DATE = new Date();
  private static final LocalTime RESERVATION_TIME = LocalTime.of(19, 30);
  private static final int NUMBER_OF_PEOPLE = 4;

  private static final RestaurantDto RESTAURANT =
      new RestaurantDto(
          1,
          "Mamma",
          "12345678000100",
          "Rua das Flores, 123",
          "São Paulo",
          "11 98765-4321",
          "Italiana",
          10,
          20,
          "contato@mamma.com",
          "senha123");

  private static final UserDto USER =
      new UserDto(
          1, "Bruna Casagrande", "12345678900", "11 99999-9999", "bruna@email.com", "senha123");

  public static Reservation existingReservation() {
    return Reservation.builder()
        .id(EXISTING_RESERVATION_ID)
        .reservationDate(RESERVATION_DATE)
        .reservationTime(RESERVATION_TIME)
        .numberOfPeople(NUMBER_OF_PEOPLE)
        .restaurant(RESTAURANT)
        .user(USER)
        .build();
  }
}
