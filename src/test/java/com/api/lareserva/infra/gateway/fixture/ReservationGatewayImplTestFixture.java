package com.api.lareserva.infra.gateway.fixture;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import com.api.lareserva.infra.persistence.entity.ReservationEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.entity.UserEntity;
import java.time.LocalTime;
import java.util.Date;

public class ReservationGatewayImplTestFixture {

  public static final Integer EXISTING_RESERVATION_ID = 1;
  public static final Date RESERVATION_DATE = new Date();
  public static final LocalTime RESERVATION_TIME = LocalTime.of(19, 30); // Corrigido para LocalTime
  public static final Integer NUMBER_OF_PEOPLE = 4;

  public static Reservation existingReservation() {
    return Reservation.builder()
        .id(EXISTING_RESERVATION_ID)
        .reservationDate(RESERVATION_DATE)
        .reservationTime(RESERVATION_TIME) // Corrigido
        .numberOfPeople(NUMBER_OF_PEOPLE)
        .restaurant(existingRestaurantDto())
        .user(existingUserDto())
        .build();
  }

  public static Reservation newReservation() {
    return Reservation.builder()
        .reservationDate(RESERVATION_DATE)
        .reservationTime(RESERVATION_TIME) // Corrigido
        .numberOfPeople(NUMBER_OF_PEOPLE)
        .restaurant(existingRestaurantDto())
        .user(existingUserDto())
        .build();
  }

  public static ReservationEntity existingReservationEntity() {
    return ReservationEntity.builder()
        .id(EXISTING_RESERVATION_ID)
        .reservationDate(RESERVATION_DATE)
        .reservationTime(RESERVATION_TIME) // Corrigido
        .numberOfPeople(NUMBER_OF_PEOPLE)
        .restaurant(existingRestaurantEntity())
        .user(existingUserEntity())
        .build();
  }

  public static RestaurantDto existingRestaurantDto() {
    return RestaurantDto.builder()
        .id(1)
        .restaurantName("Mamma")
        .cnpj("12345678901234")
        .address("Rua das Flores, 123")
        .city("São Paulo")
        .phoneNumber("11999999999")
        .typeOfFood("Italiana")
        .capacity(50)
        .numberOfTables(10)
        .email("mamma@email.com")
        .password("senha123")
        .build();
  }

  public static UserDto existingUserDto() {
    return UserDto.builder()
        .id(1)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11987654321")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }

  public static RestaurantEntity existingRestaurantEntity() {
    return RestaurantEntity.builder()
        .id(1)
        .restaurantName("Mamma")
        .cnpj("12345678901234")
        .address("Rua das Flores, 123")
        .city("São Paulo")
        .phoneNumber("11999999999")
        .typeOfFood("Italiana")
        .capacity(50)
        .numberOfTables(10)
        .email("mamma@email.com")
        .password("senha123")
        .build();
  }

  public static UserEntity existingUserEntity() {
    return UserEntity.builder()
        .id(1)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11987654321")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
