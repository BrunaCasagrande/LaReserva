package com.api.lareserva.core.usecase.exception;

import static java.lang.String.format;

public class UserNotFoundException extends BusinessException {

  private static final String ERROR_CODE = "not_found";
  private static final String MESSAGE_BY_ID = "User with id=[%s] not found.";
  private static final String MESSAGE_BY_CPF = "User with cpf=[%s] not found.";

  public UserNotFoundException(final int id) {
    super(format(MESSAGE_BY_ID, id), ERROR_CODE);
  }

  public UserNotFoundException(final String cpf) {
    super(format(MESSAGE_BY_CPF, cpf), ERROR_CODE);
  }
}
