package com.api.lareserva.infra.gateway.fixture;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.infra.persistence.entity.UserEntity;

public class UserGatewayImplTestFixture {

  public static final String NAME = "Bruna Casagrande";
  public static final String CPF = "12345678900";
  public static final String PHONE_NUMBER = "11987654321";
  public static final String EMAIL = "bruna@email.com";
  public static final String PASSWORD = "Bru123456!";
  public static final String UPDATED_PHONE_NUMBER = "11999999999";
  public static final String UPDATED_EMAIL = "new@email.com";
  public static final String UPDATED_PASSWORD = "NewPassword123!";
  public static final Integer NONEXISTENT_ID = 99;
  public static final String NONEXISTENT_CPF = "99999999999";

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

  public static UserEntity updatedUserEntity() {
    return UserEntity.builder()
        .id(1)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .email(UPDATED_EMAIL)
        .password(UPDATED_PASSWORD)
        .build();
  }

  public static User updatedUserDomain() {
    return User.builder()
        .id(1)
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .email(UPDATED_EMAIL)
        .password(UPDATED_PASSWORD)
        .build();
  }

  public static User nonExistentUserDomain() {
    return User.builder()
        .id(99)
        .name("Nonexistent User")
        .cpf("99999999999")
        .phoneNumber("11999999999")
        .email("nonexistent@email.com")
        .password("StrongPassword123!")
        .build();
  }
}
