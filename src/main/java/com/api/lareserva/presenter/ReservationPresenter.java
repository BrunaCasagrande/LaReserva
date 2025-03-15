package com.api.lareserva.presenter;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.presenter.response.ReservationPresenterResponse;
import com.api.lareserva.presenter.response.UserPresenterResponse;
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
        .user(toUserPresenterResponse(reservation))
        .build();
  }

  private UserPresenterResponse toUserPresenterResponse(final Reservation reservation) {
    if (reservation.getUser() == null) {
      return null;
    }
    return UserPresenterResponse.builder()
        .id(reservation.getUser().getId())
        .name(reservation.getUser().getName())
        .cpf(reservation.getUser().getCpf())
        .phoneNumber(reservation.getUser().getPhoneNumber())
        .email(reservation.getUser().getEmail())
        .build();
  }
}
