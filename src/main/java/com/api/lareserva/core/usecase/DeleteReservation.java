package com.api.lareserva.core.usecase;

import com.api.lareserva.core.gateway.ReservationGateway;
import com.api.lareserva.core.usecase.exception.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteReservation {

    private final ReservationGateway reservationGateway;

    public void execute(final Integer id) {
        reservationGateway.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        reservationGateway.deleteById(id);
    }
}
