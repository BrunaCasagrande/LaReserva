package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import java.time.LocalTime;
import java.util.Date;

public class SearchReservationTestFixture {

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
      new UserDto(2, "Gabi Mesquita", "98765432100", "11 98888-8888", "gabi@email.com", "senha123");

  public static Reservation validReservation() {
    return Reservation.builder()
        .id(1)
        .reservationDate(RESERVATION_DATE)
        .reservationTime(RESERVATION_TIME)
        .numberOfPeople(NUMBER_OF_PEOPLE)
        .restaurant(RESTAURANT)
        .user(USER)
        .build();
  }
}
