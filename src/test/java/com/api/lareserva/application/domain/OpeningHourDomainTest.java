package com.api.lareserva.application.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.api.lareserva.application.domain.exception.DomainException;
import com.api.lareserva.application.dto.RestaurantDto;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OpeningHourDomainTest {

  @Test
  void shouldCreateWithSuccess() {
    final var restaurant = RestaurantDto.builder().build();
    final var openingHour =
        OpeningHour.createOpeningHour(
            "Monday", LocalTime.parse("10:00"), LocalTime.parse("12:00"), restaurant);

    assertThat(openingHour.getDayOfWeek()).isEqualTo("Monday");
    assertThat(openingHour.getOpenTime()).isEqualTo(LocalTime.parse("10:00"));
    assertThat(openingHour.getCloseTime()).isEqualTo(LocalTime.parse("12:00"));
    assertThat(openingHour.getRestaurant()).isEqualTo(restaurant);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenDayOfWeekIsBlank(final String invalidDayOfWeek) {
    final var restaurant = RestaurantDto.builder().build();

    assertThatThrownBy(
            () ->
                OpeningHour.createOpeningHour(
                    invalidDayOfWeek,
                    LocalTime.parse("10:00"),
                    LocalTime.parse("12:00"),
                    restaurant))
        .isInstanceOf(DomainException.class)
        .hasMessage("Day of week is required");
  }

  @Test
  void shouldNotCreateWhenOpenTimeIsNull() {
    final var restaurant = RestaurantDto.builder().build();

    assertThatThrownBy(
            () ->
                OpeningHour.createOpeningHour("Monday", null, LocalTime.parse("12:00"), restaurant))
        .isInstanceOf(DomainException.class)
        .hasMessage("Open time is required");
  }

  @Test
  void shouldNotCreateWhenCloseTimeIsNull() {
    final var restaurant = RestaurantDto.builder().build();

    assertThatThrownBy(
            () ->
                OpeningHour.createOpeningHour("Monday", LocalTime.parse("10:00"), null, restaurant))
        .isInstanceOf(DomainException.class)
        .hasMessage("Close time is required");
  }

  @Test
  void shouldNotCreateWhenRestaurantIsNull() {
    assertThatThrownBy(
            () ->
                OpeningHour.createOpeningHour(
                    "Monday", LocalTime.parse("10:00"), LocalTime.parse("12:00"), null))
        .isInstanceOf(DomainException.class)
        .hasMessage("Restaurant is required");
  }
}
