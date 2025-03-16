package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.UpdateOpeningHourTestFixture.validExistingOpeningHour;
import static com.api.lareserva.core.usecase.fixture.UpdateOpeningHourTestFixture.validUpdateRequest;
import static com.api.lareserva.core.usecase.fixture.UpdateOpeningHourTestFixture.validUpdatedOpeningHour;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.core.gateway.OpeningHourGateway;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateOpeningHourTest {

  private OpeningHourGateway openingHourGateway;
  private UpdateOpeningHour updateOpeningHour;

  @BeforeEach
  void setup() {
    openingHourGateway = mock(OpeningHourGateway.class);
    updateOpeningHour = new UpdateOpeningHour(openingHourGateway);
  }

  @Test
  void shouldUpdateOpeningHoursSuccessfully() {
    final int restaurantId = 1;
    final var foundOpeningHours = List.of(validExistingOpeningHour());
    final var requestList = List.of(validUpdateRequest());
    final var updatedOpeningHours = List.of(validUpdatedOpeningHour());

    when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(foundOpeningHours);
    when(openingHourGateway.update(eq(restaurantId), anyList())).thenReturn(updatedOpeningHours);

    final List<OpeningHour> result = updateOpeningHour.execute(restaurantId, requestList);

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getOpenTime()).isEqualTo(LocalTime.of(10, 0));
    assertThat(result.get(0).getCloseTime()).isEqualTo(LocalTime.of(19, 0));

    verify(openingHourGateway).findByRestaurantId(restaurantId);
    verify(openingHourGateway).update(eq(restaurantId), anyList());
  }

  @Test
  void shouldCreateNewOpeningHoursWhenNoneFound() {
    final int restaurantId = 1;
    final var foundOpeningHours = List.<OpeningHour>of();
    final var requestList = List.of(validUpdateRequest());
    final var updatedOpeningHours = List.of(validUpdatedOpeningHour());

    when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(foundOpeningHours);
    when(openingHourGateway.update(eq(restaurantId), anyList())).thenReturn(updatedOpeningHours);

    final List<OpeningHour> result = updateOpeningHour.execute(restaurantId, requestList);

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getOpenTime()).isEqualTo(LocalTime.of(10, 0));
    assertThat(result.get(0).getCloseTime()).isEqualTo(LocalTime.of(19, 0));

    verify(openingHourGateway).findByRestaurantId(restaurantId);
    verify(openingHourGateway).update(eq(restaurantId), anyList());
  }
}
