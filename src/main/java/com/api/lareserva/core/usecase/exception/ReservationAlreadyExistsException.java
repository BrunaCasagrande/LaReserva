package com.api.lareserva.core.usecase.exception;

import java.time.LocalTime;
import java.util.Date;

public class ReservationAlreadyExistsException extends BusinessException {

  private static final String MESSAGE =
      "User with ID=[%d] already has a reservation on [%s] at [%s].";
  private static final String ERROR_CODE = "RESERVATION_ALREADY_EXISTS";

  public ReservationAlreadyExistsException(
      final Integer userId, final Date date, final LocalTime time) {
    super(String.format(MESSAGE, userId, date, time), ERROR_CODE);
  }
}
