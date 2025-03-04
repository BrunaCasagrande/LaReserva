package com.api.lareserva.core.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.api.lareserva.core.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

class UserDomainTest {

  @Test
  void shouldCreateWithSuccess() {
    final User user =
        User.createUser("Gabis", "12312312312", "12998765432", "gabis@email.com", "abc123!a");

    assertThat(user.getName()).isEqualTo("Gabis");
    assertThat(user.getCpf()).isEqualTo("12312312312");
    assertThat(user.getPhoneNumber()).isEqualTo("12998765432");
    assertThat(user.getEmail()).isEqualTo("gabis@email.com");
    assertThat(user.getPassword()).isEqualTo("abc123!a");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenNameIsInvalid(final String invalidName) {
    assertThatThrownBy(
            () ->
                User.createUser(
                    invalidName, "12312312312", "12998765432", "gabis@email.com", "abc123!a"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Name is required");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"123", "123456789012345", "abcdefghijklmno"})
  void shouldNotCreateWhenCpfIsInvalid(final String invalidCpf) {
    assertThatThrownBy(
            () ->
                User.createUser("Gabis", invalidCpf, "12998765432", "gabis@email.com", "abc123!a"))
        .isInstanceOf(DomainException.class)
        .hasMessage(StringUtils.isBlank(invalidCpf) ? "CPF is required" : "CPF must be 11 digits");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"12335", "1234567890123456", "abcdefg"})
  void shouldNotCreateWhenPhoneNumberIsInvalid(final String invalidPhoneNumber) {
    assertThatThrownBy(
            () ->
                User.createUser(
                    "Gabis", "12312312312", invalidPhoneNumber, "gabis@email.com", "abc123!a"))
        .isInstanceOf(DomainException.class)
        .hasMessage(
            StringUtils.isBlank(invalidPhoneNumber)
                ? "Phone number is required"
                : "Phone number must be between 10 and 15 digits");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"invalidemail", "test@", "test@.com"})
  void shouldNotCreateWhenEmailIsInvalid(final String invalidEmail) {
    assertThatThrownBy(
            () -> User.createUser("Gabis", "12312312312", "12998765432", invalidEmail, "abc123!a"))
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
                User.createUser(
                    "Gabis", "12312312312", "12998765432", "gabis@email.com", invalidPassword))
        .isInstanceOf(DomainException.class)
        .hasMessage(
            StringUtils.isBlank(invalidPassword)
                ? "Password is required"
                : "The password must be between 8 and 16 characters long, including letters, numbers and at least one special character (@$!%*?&).");
  }
}
