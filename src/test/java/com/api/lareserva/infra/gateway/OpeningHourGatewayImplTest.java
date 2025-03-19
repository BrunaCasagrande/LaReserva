package com.api.lareserva.infra.gateway;

import static com.api.lareserva.infra.gateway.fixture.OpeningHourGatewayImplTestFixture.existingOpeningHourEntity;
import static com.api.lareserva.infra.gateway.fixture.OpeningHourGatewayImplTestFixture.restaurantEntity;
import static com.api.lareserva.infra.gateway.fixture.OpeningHourGatewayImplTestFixture.updatedOpeningHour;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.repository.OpeningHourRepository;
import com.api.lareserva.infra.persistence.repository.RestaurantRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class OpeningHourGatewayImplTest {

  private final OpeningHourRepository openingHourRepository = mock(OpeningHourRepository.class);
  private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);

  private final OpeningHourGatewayImpl openingHourGateway =
      new OpeningHourGatewayImpl(openingHourRepository, restaurantRepository);

  @Test
  void shouldFindByRestaurantIdSuccessfully() {
    final var restaurantId = 1;
    final var foundEntities = List.of(existingOpeningHourEntity());

    when(openingHourRepository.findByRestaurantId(restaurantId)).thenReturn(foundEntities);

    final var response = openingHourGateway.findByRestaurantId(restaurantId);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("Monday", response.get(0).getDayOfWeek());
    assertEquals(LocalTime.of(9, 0), response.get(0).getOpenTime());
    assertEquals(LocalTime.of(18, 0), response.get(0).getCloseTime());
    assertEquals(restaurantId, response.get(0).getRestaurant().getId());

    verify(openingHourRepository).findByRestaurantId(restaurantId);
  }

  @Test
  void shouldReturnEmptyListWhenNoOpeningHoursFoundForRestaurantId() {
    final var restaurantId = 1;

    when(openingHourRepository.findByRestaurantId(restaurantId)).thenReturn(List.of());

    final var response = openingHourGateway.findByRestaurantId(restaurantId);

    assertNotNull(response);
    assertEquals(0, response.size());

    verify(openingHourRepository).findByRestaurantId(restaurantId);
  }

  @Test
  void shouldUpdateOpeningHoursSuccessfully() {
    final var restaurantId = 1;
    final var updatedOpeningHour = List.of(updatedOpeningHour());
    final var foundRestaurantEntity = restaurantEntity();

    when(restaurantRepository.findById(restaurantId))
        .thenReturn(Optional.of(foundRestaurantEntity));
    when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(foundRestaurantEntity);

    final List<OpeningHour> response = openingHourGateway.update(restaurantId, updatedOpeningHour);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("Monday", response.get(0).getDayOfWeek());
    assertEquals(LocalTime.of(10, 0), response.get(0).getOpenTime());
    assertEquals(LocalTime.of(19, 0), response.get(0).getCloseTime());

    verify(restaurantRepository).findById(1);
    verify(restaurantRepository).save(any(RestaurantEntity.class));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingOpeningHoursForNonExistentRestaurant() {
    final var nonExistentRestaurantId = 1;
    final var updatedOpeningHour = List.of(updatedOpeningHour());

    when(restaurantRepository.findById(nonExistentRestaurantId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> openingHourGateway.update(nonExistentRestaurantId, updatedOpeningHour))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Restaurant not found for id=[1].");

    verify(restaurantRepository).findById(nonExistentRestaurantId);
    verify(restaurantRepository, never()).save(any(RestaurantEntity.class));
  }
}
