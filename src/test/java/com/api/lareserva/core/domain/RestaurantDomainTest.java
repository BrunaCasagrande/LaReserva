package com.api.lareserva.core.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.api.lareserva.core.domain.exception.DomainException;
import com.api.lareserva.core.dto.OpeningHourDto;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

class RestaurantDomainTest {

  @Test
  void shouldCreateWithSuccess() {
    final OpeningHourDto openingHour =
        OpeningHourDto.builder()
            .id(1)
            .dayOfWeek("Monday")
            .openTime(LocalTime.parse("10:00"))
            .closeTime(LocalTime.parse("12:00"))
            .build();
    final Restaurant restaurant =
        Restaurant.createRestaurant(
            "Test",
            "12345678901234",
            "Test",
            "Test",
            "1234567890",
            "Test",
            50,
            10,
            List.of(openingHour),
            "test@gmail.com",
            "123aAd!12");

    assertThat(restaurant.getRestaurantName()).isEqualTo("Test");
    assertThat(restaurant.getCnpj()).isEqualTo("12345678901234");
    assertThat(restaurant.getAddress()).isEqualTo("Test");
    assertThat(restaurant.getCity()).isEqualTo("Test");
    assertThat(restaurant.getPhoneNumber()).isEqualTo("1234567890");
    assertThat(restaurant.getTypeOfFood()).isEqualTo("Test");
    assertThat(restaurant.getCapacity()).isEqualTo(50);
    assertThat(restaurant.getNumberOfTables()).isEqualTo(10);
    assertThat(restaurant.getOpeningHour()).isEqualTo(List.of(openingHour));
    assertThat(restaurant.getEmail()).isEqualTo("test@gmail.com");
    assertThat(restaurant.getPassword()).isEqualTo("123aAd!12");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenRestaurantNameIsInvalid(final String invalidName) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    invalidName,
                    "12345678901234",
                    "Test",
                    "Test",
                    "1234567890",
                    "Test",
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Restaurant name is required");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"123", "123456789012345", "abcdefghijklmno"})
  void shouldNotCreateWhenCNPJIsInvalid(final String invalidCNPJ) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    invalidCNPJ,
                    "Test",
                    "Test",
                    "1234567890",
                    "Test",
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage(
            StringUtils.isBlank(invalidCNPJ) ? "CNPJ is required" : "CNPJ must be 14 digits");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenAddressIsInvalid(final String invalidAddress) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    invalidAddress,
                    "Test",
                    "1234567890",
                    "Test",
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Address is required");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenCityIsInvalid(final String invalidCity) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    invalidCity,
                    "1234567890",
                    "Test",
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("City is required");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"12335", "1234567890123456", "abcdefg"})
  void shouldNotCreateWhenPhoneNumberIsInvalid(final String invalidPhoneNumber) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    invalidPhoneNumber,
                    "Test",
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage(
            StringUtils.isBlank(invalidPhoneNumber)
                ? "Phone number is required"
                : "Phone number must be between 10 and 15 digits");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenTypeOfFoodIsInvalid(final String invalidTypeOfFood) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    "12312312312",
                    invalidTypeOfFood,
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Type of food is required");
  }

  @Test
  void shouldNotCreateWhenCapacityIsNull() {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    "12312312312",
                    "Test",
                    null,
                    10,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Capacity is required");
  }

  @Test
  void shouldNotCreateWhenNumberOfTablesIsNull() {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    "12312312312",
                    "Test",
                    50,
                    null,
                    List.of(),
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Number of tables is required");
  }

  @Test
  void shouldNotCreateWhenOpeningHourIsNull() {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    "12312312312",
                    "Test",
                    50,
                    10,
                    null,
                    "test@gmail.com",
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Opening hour is required");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"invalidemail", "test@", "test@.com"})
  void shouldNotCreateWhenEmailIsInvalid(final String invalidEmail) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    "12312312312",
                    "Test",
                    50,
                    10,
                    List.of(),
                    invalidEmail,
                    "123aAd!12"))
        .isInstanceOf(DomainException.class)
        .hasMessage(
            StringUtils.isBlank(invalidEmail) ? "E-mail is required" : "E-mail should be valid");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"asd12", "123asd123asd123asd", "asd@!123ARdsaqwer"})
  void shouldNotCreateWhenPasswordIsInvalid(final String invalidPassword) {
    assertThatThrownBy(
            () ->
                Restaurant.createRestaurant(
                    "Test",
                    "12345678901234",
                    "Test",
                    "Test",
                    "12312312312",
                    "Test",
                    50,
                    10,
                    List.of(),
                    "test@gmail.com",
                    invalidPassword))
        .isInstanceOf(DomainException.class)
        .hasMessage(
            StringUtils.isBlank(invalidPassword)
                ? "Password is required"
                : "The password must be between 8 and 16 characters long, including letters, numbers and at least one special character (@$!%*?&).");
  }
}
