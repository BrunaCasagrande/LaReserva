package com.api.lareserva.presenter;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.presenter.response.ReservationPresenterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationPresenter {

  public ReservationPresenterResponse parseToResponse(final Reservation reservation) {
    return ReservationPresenterResponse.builder()
        .id(reservation.getId())
        .reservationDate(reservation.getReservationDate())
        .reservationTime(reservation.getReservationTime())
        .numberOfPeople(reservation.getNumberOfPeople())
            .userId(reservation.getUser().getId())
            .restaurantId(reservation.getRestaurant().getId())
        .build();
  }
}
