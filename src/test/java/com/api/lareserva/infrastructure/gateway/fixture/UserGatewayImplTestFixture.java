package com.api.lareserva.infrastructure.gateway.fixture;

import com.api.lareserva.application.domain.User;
import com.api.lareserva.infrastructure.persistence.entity.UserEntity;

public class UserGatewayImplTestFixture {

  private static final String NAME = "Bruna Casagrande";
  private static final String CPF = "12345678900";
  private static final String PHONE_NUMBER = "11987654321";
  private static final String EMAIL = "bruna@email.com";
  private static final String PASSWORD = "Bru123456!";

  public static UserEntity userEntity() {
    return UserEntity.builder()
        .id(1)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }

  public static User userDomain() {
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
