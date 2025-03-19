package com.api.lareserva.core.usecase.exception;

import static java.lang.String.format;

public class RestaurantAlreadyExistsException extends BusinessException {

  private static final String ERROR_CODE = "already_exists";
  private static final String MESSAGE = "Restaurant with CNPJ=[%s] already exists.";

  public RestaurantAlreadyExistsException(final String cnpj) {
    super(format(MESSAGE, cnpj), ERROR_CODE);
  }
}
