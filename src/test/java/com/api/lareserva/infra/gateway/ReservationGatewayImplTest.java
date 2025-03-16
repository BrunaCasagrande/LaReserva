package com.api.lareserva.infra.gateway;

import static com.api.lareserva.infra.gateway.fixture.ReservationGatewayImplTestFixture.EXISTING_RESERVATION_ID;
import static com.api.lareserva.infra.gateway.fixture.ReservationGatewayImplTestFixture.RESERVATION_DATE;
import static com.api.lareserva.infra.gateway.fixture.ReservationGatewayImplTestFixture.RESERVATION_TIME;
import static com.api.lareserva.infra.gateway.fixture.ReservationGatewayImplTestFixture.existingReservation;
import static com.api.lareserva.infra.gateway.fixture.ReservationGatewayImplTestFixture.existingReservationEntity;
import static com.api.lareserva.infra.gateway.fixture.ReservationGatewayImplTestFixture.newReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.ReservationEntity;
import com.api.lareserva.infra.persistence.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ReservationGatewayImplTest {

  private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
  private final ReservationGatewayImpl reservationGateway =
      new ReservationGatewayImpl(reservationRepository);

  @Test
  void shouldSaveReservationSuccessfully() {
    final var entity = existingReservationEntity();
    final ArgumentCaptor<ReservationEntity> captor =
        ArgumentCaptor.forClass(ReservationEntity.class);
    final var domain = newReservation();

    when(reservationRepository.save(captor.capture())).thenReturn(entity);

    final var response = reservationGateway.save(domain);

    final var captured = captor.getValue();
    assertThat(response.getId()).isEqualTo(entity.getId());
    assertThat(response.getReservationDate()).isEqualTo(captured.getReservationDate());
    assertThat(response.getReservationTime()).isEqualTo(captured.getReservationTime());
    assertThat(response.getNumberOfPeople()).isEqualTo(captured.getNumberOfPeople());

    verify(reservationRepository).save(captured);
  }

  @Test
  void shouldFindReservationByIdSuccessfully() {
    final var entity = existingReservationEntity();
    when(reservationRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

    final var response = reservationGateway.findById(entity.getId());

    assertThat(response).isPresent();
    assertThat(response)
        .hasValueSatisfying(
            reservation -> {
              assertThat(reservation.getId()).isEqualTo(entity.getId());
              assertThat(reservation.getReservationDate()).isEqualTo(entity.getReservationDate());
              assertThat(reservation.getReservationTime()).isEqualTo(entity.getReservationTime());
              assertThat(reservation.getNumberOfPeople()).isEqualTo(entity.getNumberOfPeople());
            });

    verify(reservationRepository).findById(entity.getId());
  }

  @Test
  void shouldFindReservationsByRestaurantAndDateSuccessfully() {
    final var entity = existingReservationEntity();
    when(reservationRepository.findByRestaurantIdAndReservationDate(
            entity.getRestaurant().getId(), RESERVATION_DATE))
        .thenReturn(List.of(entity));

    final var response =
        reservationGateway.findByRestaurantAndDate(
            entity.getRestaurant().getId(), RESERVATION_DATE);

    assertThat(response).hasSize(1);
    assertThat(response.get(0).getReservationDate()).isEqualTo(entity.getReservationDate());
    assertThat(response.get(0).getReservationTime()).isEqualTo(entity.getReservationTime());

    verify(reservationRepository)
        .findByRestaurantIdAndReservationDate(entity.getRestaurant().getId(), RESERVATION_DATE);
  }

  @Test
  void shouldUpdateReservationSuccessfully() {
    final var existingEntity = existingReservationEntity();
    final var updatedDomain = existingReservation();
    final var updatedEntity = existingReservationEntity();

    when(reservationRepository.findById(EXISTING_RESERVATION_ID))
        .thenReturn(Optional.of(existingEntity));
    when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(updatedEntity);

    final var response = reservationGateway.update(updatedDomain);

    assertThat(response.getReservationDate()).isEqualTo(RESERVATION_DATE);
    assertThat(response.getReservationTime()).isEqualTo(RESERVATION_TIME);

    verify(reservationRepository).findById(EXISTING_RESERVATION_ID);
    verify(reservationRepository).save(any(ReservationEntity.class));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentReservation() {
    final var nonExistentReservation = existingReservation();

    when(reservationRepository.findById(EXISTING_RESERVATION_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> reservationGateway.update(nonExistentReservation))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Reservation with ID=[1] not found.");

    verify(reservationRepository).findById(EXISTING_RESERVATION_ID);
    verify(reservationRepository, never()).save(any());
  }

  @Test
  void shouldDeleteReservationSuccessfully() {
    when(reservationRepository.findById(EXISTING_RESERVATION_ID))
        .thenReturn(Optional.of(existingReservationEntity()));

    reservationGateway.deleteById(EXISTING_RESERVATION_ID);

    verify(reservationRepository).deleteById(EXISTING_RESERVATION_ID);
  }

  @Test
  void shouldReturnEmptyWhenReservationIsNotFoundById() {
    when(reservationRepository.findById(EXISTING_RESERVATION_ID)).thenReturn(Optional.empty());

    final var response = reservationGateway.findById(EXISTING_RESERVATION_ID);

    assertThat(response).isEmpty();

    verify(reservationRepository).findById(EXISTING_RESERVATION_ID);
  }
}
