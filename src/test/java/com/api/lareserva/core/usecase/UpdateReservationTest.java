package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.UpdateReservationTestFixture.updatedReservation;
import static com.api.lareserva.core.usecase.fixture.UpdateReservationTestFixture.validReservation;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.UpdateReservationDto;
import com.api.lareserva.core.gateway.ReservationGateway;
import com.api.lareserva.core.usecase.exception.ReservationAlreadyExistsException;
import com.api.lareserva.core.usecase.exception.ReservationNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UpdateReservationTest {

  private final ReservationGateway reservationGateway = mock(ReservationGateway.class);
  private final UpdateReservation updateReservation = new UpdateReservation(reservationGateway);

  @Test
  void shouldUpdateReservationSuccessfully() {
    final var existingReservation = validReservation();
    final var updatedReservationData = updatedReservation();
    final var request = new UpdateReservationDto();
    request.setReservationDate(updatedReservationData.getReservationDate());
    request.setReservationTime(updatedReservationData.getReservationTime());
    request.setNumberOfPeople(updatedReservationData.getNumberOfPeople());

    final ArgumentCaptor<Reservation> reservationCaptor =
        ArgumentCaptor.forClass(Reservation.class);

    when(reservationGateway.findById(existingReservation.getId()))
        .thenReturn(of(existingReservation));
    when(reservationGateway.update(reservationCaptor.capture())).thenReturn(updatedReservationData);

    final var response = updateReservation.execute(existingReservation.getId(), request);

    assertThat(response.getId()).isEqualTo(updatedReservationData.getId());
    assertThat(response.getReservationDate())
        .isEqualTo(updatedReservationData.getReservationDate());
    assertThat(response.getReservationTime())
        .isEqualTo(updatedReservationData.getReservationTime());
    assertThat(response.getNumberOfPeople()).isEqualTo(updatedReservationData.getNumberOfPeople());

    verify(reservationGateway).findById(existingReservation.getId());
    verify(reservationGateway).update(reservationCaptor.getValue());

    final var capturedReservation = reservationCaptor.getValue();
    assertThat(capturedReservation.getReservationDate())
        .isEqualTo(updatedReservationData.getReservationDate());
    assertThat(capturedReservation.getReservationTime())
        .isEqualTo(updatedReservationData.getReservationTime());
    assertThat(capturedReservation.getNumberOfPeople())
        .isEqualTo(updatedReservationData.getNumberOfPeople());
  }

  @Test
  void shouldThrowExceptionWhenReservationNotFound() {
    final var request = new UpdateReservationDto();
    request.setReservationDate(new java.util.Date());
    request.setReservationTime(java.time.LocalTime.of(20, 0));

    final int invalidReservationId = 999;

    when(reservationGateway.findById(invalidReservationId)).thenReturn(java.util.Optional.empty());

    assertThatThrownBy(() -> updateReservation.execute(invalidReservationId, request))
        .isInstanceOf(ReservationNotFoundException.class)
        .hasMessage("Reservation with ID=[999] not found.");

    verify(reservationGateway).findById(invalidReservationId);
    verify(reservationGateway, never()).update(any());
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyHasReservationAtSameDateTime() {
    final var existingReservation = validReservation();

    final var anotherReservationSameUser =
        Reservation.builder()
            .id(2)
            .reservationDate(existingReservation.getReservationDate())
            .reservationTime(existingReservation.getReservationTime())
            .numberOfPeople(2)
            .restaurant(existingReservation.getRestaurant())
            .user(existingReservation.getUser())
            .build();

    final var request = new UpdateReservationDto();
    request.setReservationDate(existingReservation.getReservationDate());
    request.setReservationTime(existingReservation.getReservationTime());

    when(reservationGateway.findById(existingReservation.getId()))
        .thenReturn(Optional.of(existingReservation));

    when(reservationGateway.findByRestaurantAndDate(
            existingReservation.getRestaurant().getId(), request.getReservationDate()))
        .thenReturn(List.of(anotherReservationSameUser));

    assertThatThrownBy(() -> updateReservation.execute(existingReservation.getId(), request))
        .isInstanceOf(ReservationAlreadyExistsException.class)
        .hasMessageContaining("User with ID=[1] already has a reservation");

    verify(reservationGateway).findById(existingReservation.getId());
    verify(reservationGateway)
        .findByRestaurantAndDate(
            existingReservation.getRestaurant().getId(), request.getReservationDate());
    verify(reservationGateway, never()).update(any());
  }
}
