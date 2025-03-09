package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.User;

public class UpdateUserTestFixture {

  public static final String EXISTING_CPF = "12345678900";
  public static final String NONEXISTENT_CPF = "99999999999";

  public static User existingUser() {
    return User.builder()
        .cpf(EXISTING_CPF)
        .name("Bruna Casagrande")
        .phoneNumber("11987654321")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }

  public static User updatedUser() {
    return User.builder()
        .cpf(EXISTING_CPF)
        .name("Bruna Atualizada")
        .phoneNumber("11999999999")
        .email("bruna.atualizada@email.com")
        .password("NovaSenha123!")
        .build();
  }

  public static User nonExistentUser() {
    return User.builder()
        .cpf(NONEXISTENT_CPF)
        .name("Usuário Inexistente")
        .phoneNumber("11000000000")
        .email("inexistente@email.com")
        .password("SenhaInvalida!")
        .build();
  }
}
