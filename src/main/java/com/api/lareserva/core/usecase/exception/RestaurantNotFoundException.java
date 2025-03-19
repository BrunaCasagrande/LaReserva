package com.api.lareserva.core.usecase.exception;

import static java.lang.String.format;

public class RestaurantNotFoundException extends BusinessException {

  private static final String ERROR_CODE = "not_found";

  public RestaurantNotFoundException(final String message, final String identifier) {
    super(format(message, identifier), ERROR_CODE);
  }
}
