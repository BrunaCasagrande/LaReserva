package com.api.lareserva.application.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.api.lareserva.application.domain.exception.DomainException;
import com.api.lareserva.application.dto.RestaurantDto;
import com.api.lareserva.application.dto.UserDto;
import java.sql.Date;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationDomainTest {

  @Test
  void shouldCreateWithSuccess() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();
    final Reservation reservation =
        Reservation.createReservation(
            Date.valueOf("2023-01-01"), LocalTime.of(12, 0), 4, restaurant, user);

    assertThat(reservation.getReservationDate()).isEqualTo(Date.valueOf("2023-01-01"));
    assertThat(reservation.getReservationTime()).isEqualTo(LocalTime.of(12, 0));
    assertThat(reservation.getNumberOfPeople()).isEqualTo(4);
    assertThat(reservation.getRestaurant()).isEqualTo(restaurant);
    assertThat(reservation.getUser()).isEqualTo(user);
  }

  @Test
  void shouldNotCreateWhenReservationDateIsNull() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();

    assertThatThrownBy(
            () -> Reservation.createReservation(null, LocalTime.of(12, 0), 4, restaurant, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Reservation Date is required");
  }

  @Test
  void shouldNotCreateWhenReservationTimeIsNull() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();

    assertThatThrownBy(
            () ->
                Reservation.createReservation(
                    Date.valueOf("2023-01-01"), null, 4, restaurant, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Reservation Time is required");
  }

  @Test
  void shouldNotCreateWhenNumberOfPeopleIsNull() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();

    assertThatThrownBy(
            () ->
                Reservation.createReservation(
                    Date.valueOf("2023-01-01"), LocalTime.of(12, 0), null, restaurant, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Number of People is required");
  }

  @Test
  void shouldNotCreateWhenRestaurantIsNull() {
    final var user = UserDto.builder().build();

    assertThatThrownBy(
            () ->
                Reservation.createReservation(
                    Date.valueOf("2023-01-01"), LocalTime.of(12, 0), 4, null, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Restaurant is required");
  }

  @Test
  void shouldNotCreateWhenUserIsNull() {
    final var restaurant = RestaurantDto.builder().build();

    assertThatThrownBy(
            () ->
                Reservation.createReservation(
                    Date.valueOf("2023-01-01"), LocalTime.of(12, 0), 4, restaurant, null))
        .isInstanceOf(DomainException.class)
        .hasMessage("User is required");
  }
}
