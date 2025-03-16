package com.api.lareserva.core.usecase.exception;

import java.util.Date;

public class RatingAlreadyExistsException extends BusinessException {

  private static final String MESSAGE =
      "User with ID=[%d] has already rated Restaurant with ID=[%d] on date [%s].";
  private static final String ERROR_CODE = "RATING_ALREADY_EXISTS";

  public RatingAlreadyExistsException(
      final Integer userId, final Integer restaurantId, final Date date) {
    super(String.format(MESSAGE, userId, restaurantId, date), ERROR_CODE);
  }
}
