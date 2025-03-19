package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.UpdateRestaurantTestFixture.validRestaurantGatewayResponse;
import static com.api.lareserva.core.usecase.fixture.UpdateRestaurantTestFixture.validUpdateRestaurantRequest;
import static com.api.lareserva.core.usecase.fixture.UpdateRestaurantTestFixture.validUpdatedRestaurantResponse;
import static com.api.lareserva.core.usecase.fixture.UpdateRestaurantTestFixture.validUpdatedRestaurantResponseWithSingleField;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.UpdateRestaurantDto;
import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.core.usecase.exception.RestaurantNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UpdateRestaurantTest {

  private RestaurantGateway restaurantGateway;
  private UpdateRestaurant updateRestaurant;

  @BeforeEach
  void setup() {
    restaurantGateway = mock(RestaurantGateway.class);
    updateRestaurant = new UpdateRestaurant(restaurantGateway);
  }

  @Test
  void shouldUpdateRestaurantWithAllFieldsSuccessfully() {
    final var cnpj = "12345678901234";
    final var foundRestaurant = validRestaurantGatewayResponse();
    final var updateRequest = validUpdateRestaurantRequest();
    final var updatedRestaurant = validUpdatedRestaurantResponse();
    final ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);

    when(restaurantGateway.findByCnpj(cnpj)).thenReturn(Optional.of(foundRestaurant));
    when(restaurantGateway.update(restaurantCaptor.capture())).thenReturn(updatedRestaurant);

    final var response = updateRestaurant.execute(cnpj, updateRequest);

    assertThat(response.getId()).isEqualTo(updatedRestaurant.getId());
    assertThat(response.getRestaurantName()).isEqualTo(updatedRestaurant.getRestaurantName());
    assertThat(response.getCnpj()).isEqualTo(updatedRestaurant.getCnpj());
    assertThat(response.getAddress()).isEqualTo(updateRequest.address());
    assertThat(response.getPhoneNumber()).isEqualTo(updateRequest.phoneNumber());
    assertThat(response.getTypeOfFood()).isEqualTo(updateRequest.typeOfFood());
    assertThat(response.getCapacity()).isEqualTo(updateRequest.capacity());
    assertThat(response.getNumberOfTables()).isEqualTo(updateRequest.numberOfTables());
    assertThat(response.getOpeningHour()).isEqualTo(updatedRestaurant.getOpeningHour());
    assertThat(response.getEmail()).isEqualTo(updateRequest.email());

    verify(restaurantGateway).findByCnpj(cnpj);
    verify(restaurantGateway).update(restaurantCaptor.getValue());
  }

  @Test
  void shouldUpdateOnlyAddress() {
    testSingleFieldUpdate("Rua dos Campos, n 200", null, null, null, null, null);
  }

  @Test
  void shouldUpdateOnlyPhoneNumber() {
    testSingleFieldUpdate(null, "9876543210", null, null, null, null);
  }

  @Test
  void shouldUpdateOnlyTypeOfFood() {
    testSingleFieldUpdate(null, null, "Japanese", null, null, null);
  }

  @Test
  void shouldUpdateOnlyCapacity() {
    testSingleFieldUpdate(null, null, null, 100, null, null);
  }

  @Test
  void shouldUpdateOnlyNumberOfTables() {
    testSingleFieldUpdate(null, null, null, null, 20, null);
  }

  @Test
  void shouldUpdateOnlyEmail() {
    testSingleFieldUpdate(null, null, null, null, null, "updated@gmail.com");
  }

  @Test
  void shouldUpdateOnlyPassword() {
    final var newPassword = "newSecurePassword123!";
    final var cnpj = "12345678901234";
    final var foundRestaurant = validRestaurantGatewayResponse();
    final var updateRequest =
        new UpdateRestaurantDto(null, null, null, null, null, null, newPassword);
    final ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);

    when(restaurantGateway.findByCnpj(cnpj)).thenReturn(Optional.of(foundRestaurant));
    when(restaurantGateway.update(any(Restaurant.class))).thenReturn(foundRestaurant);

    updateRestaurant.execute(cnpj, updateRequest);

    verify(restaurantGateway).update(restaurantCaptor.capture());
    Restaurant updatedRestaurant = restaurantCaptor.getValue();

    assertThat(updatedRestaurant.getPassword()).isEqualTo(newPassword);

    assertThat(updatedRestaurant.getId()).isEqualTo(foundRestaurant.getId());
    assertThat(updatedRestaurant.getRestaurantName())
        .isEqualTo(foundRestaurant.getRestaurantName());
    assertThat(updatedRestaurant.getCnpj()).isEqualTo(foundRestaurant.getCnpj());
    assertThat(updatedRestaurant.getAddress()).isEqualTo(foundRestaurant.getAddress());
    assertThat(updatedRestaurant.getPhoneNumber()).isEqualTo(foundRestaurant.getPhoneNumber());
    assertThat(updatedRestaurant.getTypeOfFood()).isEqualTo(foundRestaurant.getTypeOfFood());
    assertThat(updatedRestaurant.getCapacity()).isEqualTo(foundRestaurant.getCapacity());
    assertThat(updatedRestaurant.getNumberOfTables())
        .isEqualTo(foundRestaurant.getNumberOfTables());
    assertThat(updatedRestaurant.getEmail()).isEqualTo(foundRestaurant.getEmail());
    assertThat(updatedRestaurant.getOpeningHour()).isEqualTo(foundRestaurant.getOpeningHour());
  }

  @Test
  void shouldNotUpdateRestaurantWhenItDoesNotExist() {
    final var cnpj = "12345678901234";
    final var updateRequest = validUpdateRestaurantRequest();

    when(restaurantGateway.findByCnpj("12345678901234")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateRestaurant.execute(cnpj, updateRequest))
        .isInstanceOf(RestaurantNotFoundException.class)
        .hasMessage("Restaurant with CNPJ=[12345678901234] not found.");

    verify(restaurantGateway).findByCnpj("12345678901234");
    verify(restaurantGateway, never()).update(any());
  }

  private void testSingleFieldUpdate(
      String address,
      String phoneNumber,
      String typeOfFood,
      Integer capacity,
      Integer numberOfTables,
      String email) {

    final var cnpj = "12345678901234";
    final var foundRestaurant = validRestaurantGatewayResponse();
    final var updateRequest =
        new UpdateRestaurantDto(
            address, phoneNumber, typeOfFood, capacity, numberOfTables, email, null);
    final var expectedUpdatedRestaurant =
        validUpdatedRestaurantResponseWithSingleField(foundRestaurant, updateRequest);

    when(restaurantGateway.findByCnpj(cnpj)).thenReturn(Optional.of(foundRestaurant));
    when(restaurantGateway.update(any(Restaurant.class))).thenReturn(expectedUpdatedRestaurant);

    final var response = updateRestaurant.execute(cnpj, updateRequest);

    assertThat(response.getId()).isEqualTo(foundRestaurant.getId());
    assertThat(response.getRestaurantName()).isEqualTo(foundRestaurant.getRestaurantName());
    assertThat(response.getCnpj()).isEqualTo(foundRestaurant.getCnpj());
    assertThat(response.getAddress())
        .isEqualTo(address != null ? address : foundRestaurant.getAddress());
    assertThat(response.getPhoneNumber())
        .isEqualTo(phoneNumber != null ? phoneNumber : foundRestaurant.getPhoneNumber());
    assertThat(response.getTypeOfFood())
        .isEqualTo(typeOfFood != null ? typeOfFood : foundRestaurant.getTypeOfFood());
    assertThat(response.getCapacity())
        .isEqualTo(capacity != null ? capacity : foundRestaurant.getCapacity());
    assertThat(response.getNumberOfTables())
        .isEqualTo(numberOfTables != null ? numberOfTables : foundRestaurant.getNumberOfTables());
    assertThat(response.getEmail()).isEqualTo(email != null ? email : foundRestaurant.getEmail());

    verify(restaurantGateway).findByCnpj(cnpj);
    verify(restaurantGateway).update(any(Restaurant.class));
  }
}
