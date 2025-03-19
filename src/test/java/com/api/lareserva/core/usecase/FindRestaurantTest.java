package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.FindRestaurantTestFixture.validRestaurantGatewayResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.RestaurantGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class FindRestaurantTest {

  private final RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);

  private final FindRestaurant findRestaurant = new FindRestaurant(restaurantGateway);

  @Test
  void shouldFindRestaurantSuccessfully() {
    final var restaurant = validRestaurantGatewayResponse();

    when(restaurantGateway.findByCnpj("12345678901234")).thenReturn(Optional.of(restaurant));

    final var response = findRestaurant.execute("12345678901234");

    assertThat(response).isPresent();
    assertThat(response).contains(restaurant);

    verify(restaurantGateway).findByCnpj("12345678901234");
  }

  @Test
  void shouldReturnEmptyWhenRestaurantDoesNotExist() {
    when(restaurantGateway.findByCnpj("12345678901234")).thenReturn(Optional.empty());

    final var response = findRestaurant.execute("12345678901234");

    assertThat(response).isEmpty();

    verify(restaurantGateway).findByCnpj("12345678901234");
  }
}
