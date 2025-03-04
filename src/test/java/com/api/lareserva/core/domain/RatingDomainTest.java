package com.api.lareserva.core.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.api.lareserva.core.domain.exception.DomainException;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import java.sql.Date;
import org.junit.jupiter.api.Test;

class RatingDomainTest {

  @Test
  void shouldCreateWithSuccess() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();
    final var rating = Rating.createRating(5, "Test", Date.valueOf("2023-01-01"), restaurant, user);

    assertThat(rating.getStars()).isEqualTo(5);
    assertThat(rating.getComment()).isEqualTo("Test");
    assertThat(rating.getDate()).isEqualTo(Date.valueOf("2023-01-01"));
    assertThat(rating.getRestaurant()).isEqualTo(restaurant);
    assertThat(rating.getUser()).isEqualTo(user);
  }

  @Test
  void shouldNotCreateWhenStarsIsNull() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();

    assertThatThrownBy(
            () -> Rating.createRating(null, "Test", Date.valueOf("2023-01-01"), restaurant, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Stars is required");
  }

  @Test
  void shouldCreateWhenCommentIsNull() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();
    final var rating = Rating.createRating(5, null, Date.valueOf("2023-01-01"), restaurant, user);

    assertThat(rating.getStars()).isEqualTo(5);
    assertThat(rating.getComment()).isNull();
    assertThat(rating.getDate()).isEqualTo(Date.valueOf("2023-01-01"));
    assertThat(rating.getRestaurant()).isEqualTo(restaurant);
    assertThat(rating.getUser()).isEqualTo(user);
  }

  @Test
  void shouldNotCreateWhenDateIsNull() {
    final var restaurant = RestaurantDto.builder().build();
    final var user = UserDto.builder().build();

    assertThatThrownBy(() -> Rating.createRating(5, "Test", null, restaurant, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Date is required");
  }

  @Test
  void shouldNotCreateWhenRestaurantIsNull() {
    final var user = UserDto.builder().build();

    assertThatThrownBy(() -> Rating.createRating(5, "Test", Date.valueOf("2023-01-01"), null, user))
        .isInstanceOf(DomainException.class)
        .hasMessage("Restaurant is required");
  }

  @Test
  void shouldNotCreateWhenUserIsNull() {
    final var restaurant = RestaurantDto.builder().build();

    assertThatThrownBy(
            () -> Rating.createRating(5, "Test", Date.valueOf("2023-01-01"), restaurant, null))
        .isInstanceOf(DomainException.class)
        .hasMessage("User is required");
  }
}
