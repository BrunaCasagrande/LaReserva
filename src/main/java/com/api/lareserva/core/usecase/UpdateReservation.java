package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.gateway.ReservationGateway;
import com.api.lareserva.core.usecase.exception.ReservationNotFoundException;
import com.api.lareserva.entrypoint.controller.request.UpdateReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateReservation {

    private final ReservationGateway reservationGateway;

    public Reservation execute(final Integer id, final UpdateReservationRequest request) {

        final Reservation existingReservation = reservationGateway.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (request.getReservationDate() != null) {
            existingReservation.setReservationDate(request.getReservationDate());
        }
        if (request.getReservationTime() != null) {
            existingReservation.setReservationTime(request.getReservationTime());
        }
        if (request.getNumberOfPeople() != null) {
            existingReservation.setNumberOfPeople(request.getNumberOfPeople());
        }

        return reservationGateway.update(existingReservation);
    }
}
