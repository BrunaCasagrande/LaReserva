package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.CreateRestaurantTestFixture.validRestaurantGatewayResponse;
import static com.api.lareserva.core.usecase.fixture.CreateRestaurantTestFixture.validRestaurantRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.core.usecase.exception.RestaurantAlreadyExistsException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreateRestaurantTest {

  private final RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);

  private final CreateRestaurant createRestaurant = new CreateRestaurant(restaurantGateway);

  @Test
  void shouldCreateRestaurantSuccessfully() {
    final var request = validRestaurantRequest();
    final var gatewayResponse = validRestaurantGatewayResponse();
    final ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);

    when(restaurantGateway.findByCnpj(request.getCnpj())).thenReturn(Optional.empty());
    when(restaurantGateway.save(restaurantCaptor.capture())).thenReturn(gatewayResponse);

    final var response = createRestaurant.execute(request);

    assertThat(response.getId()).isEqualTo(gatewayResponse.getId());
    assertThat(response.getRestaurantName()).isEqualTo(gatewayResponse.getRestaurantName());
    assertThat(response.getCnpj()).isEqualTo(gatewayResponse.getCnpj());
    assertThat(response.getAddress()).isEqualTo(gatewayResponse.getAddress());
    assertThat(response.getCity()).isEqualTo(gatewayResponse.getCity());
    assertThat(response.getPhoneNumber()).isEqualTo(gatewayResponse.getPhoneNumber());
    assertThat(response.getTypeOfFood()).isEqualTo(gatewayResponse.getTypeOfFood());
    assertThat(response.getCapacity()).isEqualTo(gatewayResponse.getCapacity());
    assertThat(response.getNumberOfTables()).isEqualTo(gatewayResponse.getNumberOfTables());
    assertThat(response.getOpeningHour()).isEqualTo(gatewayResponse.getOpeningHour());
    assertThat(response.getEmail()).isEqualTo(gatewayResponse.getEmail());

    verify(restaurantGateway).findByCnpj(request.getCnpj());
    verify(restaurantGateway).save(restaurantCaptor.getValue());
  }

  @Test
  void shouldNotCreateRestaurantWhenItAlreadyExists() {
    final var request = validRestaurantRequest();
    final var gatewayResponse = validRestaurantGatewayResponse();

    when(restaurantGateway.findByCnpj(request.getCnpj())).thenReturn(Optional.of(gatewayResponse));

    assertThatThrownBy(() -> createRestaurant.execute(request))
        .isInstanceOf(RestaurantAlreadyExistsException.class)
        .hasMessage("Restaurant with CNPJ=[12345678901234] already exists.");

    verify(restaurantGateway).findByCnpj(request.getCnpj());
    verify(restaurantGateway, never()).save(request);
  }
}
