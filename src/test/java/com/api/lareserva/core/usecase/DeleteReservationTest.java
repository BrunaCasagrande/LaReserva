package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.DeleteReservationTestFixture.EXISTING_RESERVATION_ID;
import static com.api.lareserva.core.usecase.fixture.DeleteReservationTestFixture.existingReservation;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.ReservationGateway;
import com.api.lareserva.core.usecase.exception.ReservationNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DeleteReservationTest {

  private final ReservationGateway reservationGateway = mock(ReservationGateway.class);
  private final DeleteReservation deleteReservation = new DeleteReservation(reservationGateway);

  @Test
  void shouldDeleteReservationSuccessfully() {
    when(reservationGateway.findById(EXISTING_RESERVATION_ID))
        .thenReturn(Optional.of(existingReservation()));

    deleteReservation.execute(EXISTING_RESERVATION_ID);

    verify(reservationGateway).findById(EXISTING_RESERVATION_ID);
    verify(reservationGateway).deleteById(EXISTING_RESERVATION_ID);
  }

  @Test
  void shouldThrowExceptionWhenReservationDoesNotExist() {
    when(reservationGateway.findById(EXISTING_RESERVATION_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> deleteReservation.execute(EXISTING_RESERVATION_ID))
        .isInstanceOf(ReservationNotFoundException.class)
        .hasMessage("Reservation with ID=[1] not found.");

    verify(reservationGateway).findById(EXISTING_RESERVATION_ID);
    verify(reservationGateway, never()).deleteById(anyInt());
  }
}
