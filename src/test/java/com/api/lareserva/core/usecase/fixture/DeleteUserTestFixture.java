package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.User;

public class DeleteUserTestFixture {

  public static final String EXISTING_CPF = "12345678900";

  public static User existingUser() {
    return User.builder()
        .cpf(EXISTING_CPF)
        .name("Bruna Casagrande")
        .phoneNumber("11987654321")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
