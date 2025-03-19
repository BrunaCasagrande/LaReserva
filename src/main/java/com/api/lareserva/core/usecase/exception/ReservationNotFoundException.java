package com.api.lareserva.core.usecase.exception;

public class ReservationNotFoundException extends BusinessException {

  private static final String MESSAGE = "Reservation with ID=[%d] not found.";
  private static final String ERROR_CODE = "RESERVATION_NOT_FOUND";

  public ReservationNotFoundException(final Integer id) {
    super(String.format(MESSAGE, id), ERROR_CODE);
  }
}
