package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.gateway.ReservationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateReservation {

    private final ReservationGateway reservationGateway;

    public Reservation execute(final Reservation request) {

        final var buildDomain = Reservation.createReservation(
                request.getReservationDate(),
                request.getReservationTime(),
                request.getNumberOfPeople(),
                request.getRestaurant(),
                request.getUser()
        );

        return reservationGateway.save(buildDomain);
    }
}
