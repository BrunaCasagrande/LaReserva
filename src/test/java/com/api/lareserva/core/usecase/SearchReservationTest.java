package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.SearchReservationTestFixture.validReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.gateway.ReservationGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SearchReservationTest {

  private final ReservationGateway reservationGateway = mock(ReservationGateway.class);
  private final SearchReservation searchReservation = new SearchReservation(reservationGateway);

  @Test
  void shouldFindReservationByIdSuccessfully() {
    final int reservationId = 1;
    final Reservation expectedReservation = validReservation();

    when(reservationGateway.findById(reservationId)).thenReturn(Optional.of(expectedReservation));

    final var result = searchReservation.execute(reservationId);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(reservationId);
    assertThat(result.get().getUser().getId()).isEqualTo(expectedReservation.getUser().getId());
    assertThat(result.get().getRestaurant().getId())
        .isEqualTo(expectedReservation.getRestaurant().getId());

    verify(reservationGateway).findById(reservationId);
  }

  @Test
  void shouldReturnEmptyWhenReservationNotFound() {
    final int invalidReservationId = 99;

    when(reservationGateway.findById(invalidReservationId)).thenReturn(Optional.empty());

    final var result = searchReservation.execute(invalidReservationId);

    assertThat(result).isEmpty();

    verify(reservationGateway).findById(invalidReservationId);
  }
}
