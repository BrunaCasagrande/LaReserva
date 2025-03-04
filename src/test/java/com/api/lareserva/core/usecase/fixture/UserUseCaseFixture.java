package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.User;

public class UserUseCaseFixture {
  public static final String NONEXISTENT_CPF = "99999999999";

  public static User validUserDomain() {
    return User.builder()
        .id(1)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11987654321")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
