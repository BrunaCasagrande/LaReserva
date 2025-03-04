package com.api.lareserva.infra.controller.fixture;

import com.api.lareserva.core.domain.User;

public class UserControllerTestFixture {

  public static final Integer ID = 1;
  public static final String NAME = "Bruna Casagrande";
  public static final String CPF = "12345678900";
  public static final String PHONE_NUMBER = "11987654321";
  public static final String EMAIL = "bruna@email.com";
  public static final String PASSWORD = "Bru123456!";
  public static final String UPDATED_PHONE_NUMBER = "11999999999";
  public static final String UPDATED_EMAIL = "nova@email.com";
  public static final String UPDATED_PASSWORD = "NewPassword123!";

  public static User validRequest() {
    return User.builder()
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static User validResponse() {
    return User.builder()
        .id(1)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static User validDomain() {
    return User.builder()
        .id(ID)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static User updatedRequest() {
    return User.builder()
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .email(UPDATED_EMAIL)
        .password(UPDATED_PASSWORD)
        .build();
  }

  public static User updatedResponse() {
    return User.builder()
        .id(ID)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .email(UPDATED_EMAIL)
        .password(UPDATED_PASSWORD)
        .build();
  }

  public static User validRequestWithId() {
    return User.builder()
        .id(ID)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }
}
