package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.CreateReservationTestFixture.validReservationGatewayResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.gateway.ReservationGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreateReservationTest {

  private final ReservationGateway reservationGateway = mock(ReservationGateway.class);
  private final CreateReservation createReservation = new CreateReservation(reservationGateway);

  @Test
  void shouldCreateAReservationSuccessfully() {
    final var reservationRequest = validReservationGatewayResponse();
    final ArgumentCaptor<Reservation> reservationCaptor =
        ArgumentCaptor.forClass(Reservation.class);

    when(reservationGateway.save(reservationCaptor.capture())).thenReturn(reservationRequest);

    final var response = createReservation.execute(reservationRequest);

    assertThat(response.getId()).isEqualTo(1);
    assertThat(response.getReservationDate()).isEqualTo(reservationRequest.getReservationDate());
    assertThat(response.getReservationTime()).isEqualTo(reservationRequest.getReservationTime());
    assertThat(response.getNumberOfPeople()).isEqualTo(reservationRequest.getNumberOfPeople());
    assertThat(response.getRestaurant().getRestaurantName()).isEqualTo("Mamma");
    assertThat(response.getUser().getName()).isEqualTo("Bruna Casagrande");

    verify(reservationGateway).save(reservationCaptor.getValue());
  }
}
