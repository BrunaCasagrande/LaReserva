package com.api.lareserva.infra.controller.fixture;

import com.api.lareserva.core.domain.User;

public class UserControllerTestFixture {

  private static final String NAME = "Bruna Casagrande";
  private static final String CPF = "12345678900";
  private static final String PHONE_NUMBER = "11987654321";
  private static final String EMAIL = "bruna@email.com";
  private static final String PASSWORD = "Bru123456!";

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
}
