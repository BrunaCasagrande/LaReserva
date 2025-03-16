package com.api.lareserva.entrypoint.controller.fixture;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ReservationControllerTestFixture {

  public static final Integer EXISTING_RESERVATION_ID = 1;

  public static final LocalDate LOCAL_RESERVATION_DATE = LocalDate.now();
  public static final Date RESERVATION_DATE =
      Date.from(LOCAL_RESERVATION_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());

  public static final LocalTime RESERVATION_TIME = LocalTime.of(19, 30);
  public static final Integer NUMBER_OF_PEOPLE = 4;

  public static Reservation validRequest() {
    return Reservation.builder()
        .id(EXISTING_RESERVATION_ID)
        .reservationDate(RESERVATION_DATE)
        .reservationTime(RESERVATION_TIME)
        .numberOfPeople(NUMBER_OF_PEOPLE)
        .restaurant(validRestaurant())
        .user(validUser())
        .build();
  }

  public static Reservation validResponse() {
    return validRequest();
  }

  public static Reservation updatedRequest() {
    LocalDate updatedLocalDate = LocalDate.now().plusDays(1);
    Date updatedDate = Date.from(updatedLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    return Reservation.builder()
        .id(EXISTING_RESERVATION_ID)
        .reservationDate(updatedDate)
        .reservationTime(LocalTime.of(20, 0))
        .numberOfPeople(NUMBER_OF_PEOPLE + 2)
        .restaurant(validRestaurant())
        .user(validUser())
        .build();
  }

  public static Reservation updatedResponse() {
    return updatedRequest();
  }

  private static RestaurantDto validRestaurant() {
    return RestaurantDto.builder()
        .id(10)
        .restaurantName("Mamma Mia")
        .cnpj("12345678901234")
        .address("Rua das Pizzas, 45")
        .city("São Paulo")
        .phoneNumber("11987654321")
        .typeOfFood("Italiana")
        .capacity(50)
        .numberOfTables(10)
        .email("contato@mammamia.com")
        .password("SenhaSegura123")
        .build();
  }

  private static UserDto validUser() {
    return UserDto.builder()
        .id(5)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11999999999")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
