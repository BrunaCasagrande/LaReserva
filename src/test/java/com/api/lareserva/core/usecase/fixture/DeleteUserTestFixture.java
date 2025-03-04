package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.User;

public class DeleteUserTestFixture {

  public static final Integer EXISTING_ID = 1;

  public static User existingUser() {
    return User.builder()
        .id(EXISTING_ID)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11987654321")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
