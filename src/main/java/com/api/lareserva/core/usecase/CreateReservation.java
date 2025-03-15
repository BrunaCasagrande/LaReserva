package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.gateway.ReservationGateway;
import com.api.lareserva.core.usecase.exception.ReservationAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateReservation {

  private final ReservationGateway reservationGateway;

  public Reservation execute(final Reservation request) {

    final var existingReservations =
        reservationGateway.findByRestaurantAndDate(
            request.getRestaurant().getId(), request.getReservationDate());

    boolean userHasReservation =
        existingReservations.stream()
            .anyMatch(
                reservation ->
                    reservation.getUser().getId().equals(request.getUser().getId())
                        && reservation.getReservationTime().equals(request.getReservationTime()));

    if (userHasReservation) {
      throw new ReservationAlreadyExistsException(
          request.getUser().getId(), request.getReservationDate(), request.getReservationTime());
    }

    final var buildDomain =
        Reservation.createReservation(
            request.getReservationDate(),
            request.getReservationTime(),
            request.getNumberOfPeople(),
            request.getRestaurant(),
            request.getUser());

    return reservationGateway.save(buildDomain);
  }
}
