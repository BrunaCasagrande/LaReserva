package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.User;

public class CreateUserTestFixture {

  public static final String NAME = "Bruna Casagrande";
  public static final String CPF = "12345678900";
  public static final String PHONE_NUMBER = "11987654321";
  public static final String EMAIL = "bruna@email.com";
  public static final String PASSWORD = "Bru123456!";

  public static User validUserGatewayResponse() {
    return User.builder()
        .id(1)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static User validUserRequest() {
    return User.builder()
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }
}
