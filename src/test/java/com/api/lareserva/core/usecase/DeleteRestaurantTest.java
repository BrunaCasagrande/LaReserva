package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.DeleteRestaurantTestFixture.validRestaurantGatewayResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.core.usecase.exception.RestaurantNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DeleteRestaurantTest {

  private final RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);

  private final DeleteRestaurant deleteRestaurant = new DeleteRestaurant(restaurantGateway);

  @Test
  void shouldDeleteRestaurantSuccessfully() {
    final var restaurant = validRestaurantGatewayResponse();

    when(restaurantGateway.findByCnpj("12345678901234")).thenReturn(Optional.of(restaurant));
    doNothing().when(restaurantGateway).deleteByCnpj("12345678901234");

    deleteRestaurant.execute("12345678901234");

    verify(restaurantGateway).findByCnpj("12345678901234");
    verify(restaurantGateway).deleteByCnpj("12345678901234");
  }

  @Test
  void shouldNotDeleteRestaurantWhenItDoesNotExist() {

    when(restaurantGateway.findByCnpj("12345678901234")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> deleteRestaurant.execute("12345678901234"))
        .isInstanceOf(RestaurantNotFoundException.class)
        .hasMessage("Restaurant with CNPJ=[12345678901234] not found.");

    verify(restaurantGateway).findByCnpj("12345678901234");
    verify(restaurantGateway, never()).deleteByCnpj("12345678901234");
  }
}
