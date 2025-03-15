package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.gateway.ReservationGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchReservation {

    private final ReservationGateway reservationGateway;

    public Optional<Reservation> execute(final Integer id) {
        return reservationGateway.findById(id);
    }
}
