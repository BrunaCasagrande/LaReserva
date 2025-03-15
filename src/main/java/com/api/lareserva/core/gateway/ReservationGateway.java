package com.api.lareserva.core.gateway;

import com.api.lareserva.core.domain.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationGateway {
    Reservation save(final Reservation reservation);

    Optional<Reservation> findById(final Integer id);

    List<Reservation> findByRestaurantAndDate(final Integer restaurantId, final Date reservationDate);

    Reservation update(final Reservation reservation);

    void deleteById(final Integer id);
}
