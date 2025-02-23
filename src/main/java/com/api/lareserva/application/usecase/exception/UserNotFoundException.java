package com.api.lareserva.application.usecase.exception;

import static java.lang.String.format;

public class UserNotFoundException extends BusinessException {

  private static final String ERROR_CODE = "not_found";
  private static final String MESSAGE = "User with id=[%s] not found.";

  public UserNotFoundException(final int id) {
    super(format(MESSAGE, id), ERROR_CODE);
  }
}
