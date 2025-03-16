package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.SearchRestaurantsTestFixture.validRestaurantGatewayResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.RestaurantGateway;
import java.util.List;
import org.junit.jupiter.api.Test;

class SearchRestaurantsTest {

  private final RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);

  private final SearchRestaurants searchRestaurants = new SearchRestaurants(restaurantGateway);

  @Test
  void shouldFindRestaurantsSuccessfully() {
    final var restaurantName = "Doce Sonho";
    final var city = "Natal";
    final var typeOfFood = "Italian";
    final var restaurant = validRestaurantGatewayResponse();

    when(restaurantGateway.findAllBy(restaurantName, city, typeOfFood))
        .thenReturn(List.of(restaurant));

    final var response = searchRestaurants.execute(restaurantName, city, typeOfFood);

    assertThat(response).isEqualTo(List.of(restaurant));

    verify(restaurantGateway).findAllBy(restaurantName, city, typeOfFood);
  }

  @Test
  void shouldReturnEmptyListWhenThereAreNoRestaurantsWithTheSpecifiedParameters() {
    final var restaurantName = "Doce Sonho";
    final var city = "Natal";
    final var typeOfFood = "Italian";

    when(restaurantGateway.findAllBy(restaurantName, city, typeOfFood)).thenReturn(List.of());

    final var response = searchRestaurants.execute(restaurantName, city, typeOfFood);

    assertThat(response).isEqualTo(List.of());

    verify(restaurantGateway).findAllBy(restaurantName, city, typeOfFood);
  }
}
