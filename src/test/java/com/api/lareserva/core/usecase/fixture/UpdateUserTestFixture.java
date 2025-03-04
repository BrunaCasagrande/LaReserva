package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.User;

public class UpdateUserTestFixture {

  public static final Integer EXISTING_ID = 1;
  public static final Integer NONEXISTENT_ID = 99;
  public static final String NAME = "Bruna Casagrande";
  public static final String CPF = "12345678900";
  public static final String PHONE_NUMBER = "11987654321";
  public static final String EMAIL = "bruna@email.com";
  public static final String PASSWORD = "Bru123456!";
  public static final String UPDATED_PHONE_NUMBER = "11999999999";
  public static final String UPDATED_EMAIL = "novo@email.com";
  public static final String UPDATED_PASSWORD = "NovaSenha123!";

  public static User existingUser() {
    return User.builder()
        .id(EXISTING_ID)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static User updatedUser() {
    return User.builder()
        .id(EXISTING_ID)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .email(UPDATED_EMAIL)
        .password(UPDATED_PASSWORD)
        .build();
  }

  public static User nonExistentUser() {
    return User.builder()
        .id(NONEXISTENT_ID)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .email(UPDATED_EMAIL)
        .password(UPDATED_PASSWORD)
        .build();
  }
}
