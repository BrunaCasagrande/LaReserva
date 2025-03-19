package com.api.lareserva.core.usecase.exception;

public class UserNotFoundException extends BusinessException {

  private static final String MESSAGE = "User with CPF=[%s] not found.";
  private static final String ERROR_CODE = "USER_NOT_FOUND";

  public UserNotFoundException(final String cpf) {
    super(String.format(MESSAGE, cpf), ERROR_CODE);
  }
}
