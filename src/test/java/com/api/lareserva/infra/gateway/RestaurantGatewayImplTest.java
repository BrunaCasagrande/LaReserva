package com.api.lareserva.infra.gateway;

import static com.api.lareserva.infra.gateway.fixture.RestaurantGatewayImplTestFixture.updateRestaurantEntity;
import static com.api.lareserva.infra.gateway.fixture.RestaurantGatewayImplTestFixture.validOpeningHourEntity;
import static com.api.lareserva.infra.gateway.fixture.RestaurantGatewayImplTestFixture.validRestaurantDomain;
import static com.api.lareserva.infra.gateway.fixture.RestaurantGatewayImplTestFixture.validRestaurantEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.OpeningHourEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.repository.OpeningHourRepository;
import com.api.lareserva.infra.persistence.repository.RestaurantRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

class RestaurantGatewayImplTest {

  private final OpeningHourRepository openingHourRepository = mock(OpeningHourRepository.class);
  private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);

  private final RestaurantGatewayImpl restaurantGateway =
      new RestaurantGatewayImpl(openingHourRepository, restaurantRepository);

  @Test
  void shouldSaveSuccessfully() {
    final var restaurant = validRestaurantDomain();
    final var restaurantEntity = validRestaurantEntity();
    final var openingHourEntity = validOpeningHourEntity();
    final ArgumentCaptor<List<OpeningHourEntity>> captorOpeningHour =
        ArgumentCaptor.forClass(List.class);

    when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);
    when(openingHourRepository.saveAll(captorOpeningHour.capture()))
        .thenReturn((List.of(openingHourEntity)));

    final var response = restaurantGateway.save(restaurant);

    assertNotNull(response);
    assertEquals(1, response.getId());
    assertEquals(restaurant.getRestaurantName(), response.getRestaurantName());
    assertEquals(restaurant.getCnpj(), response.getCnpj());
    assertEquals(restaurant.getAddress(), response.getAddress());
    assertEquals(restaurant.getCity(), response.getCity());
    assertEquals(restaurant.getPhoneNumber(), response.getPhoneNumber());
    assertEquals(restaurant.getTypeOfFood(), response.getTypeOfFood());
    assertEquals(restaurant.getCapacity(), response.getCapacity());
    assertEquals(restaurant.getNumberOfTables(), response.getNumberOfTables());
    assertEquals(restaurant.getEmail(), response.getEmail());
    assertNotNull(response.getOpeningHour());
    assertEquals(1, response.getOpeningHour().size());

    final var responseOpeningHour = response.getOpeningHour().get(0);
    assertEquals(openingHourEntity.getDayOfWeek(), responseOpeningHour.getDayOfWeek());
    assertEquals(openingHourEntity.getOpenTime(), responseOpeningHour.getOpenTime());
    assertEquals(openingHourEntity.getCloseTime(), responseOpeningHour.getCloseTime());

    verify(restaurantRepository).save(any(RestaurantEntity.class));
    verify(openingHourRepository).saveAll(captorOpeningHour.getValue());
  }

  @Test
  void shouldFindByCnpjSuccessfully() {
    final var cnpj = "12345678901234";
    final var restaurantEntity = validRestaurantEntity();

    when(restaurantRepository.findByCnpj(cnpj)).thenReturn(Optional.of(restaurantEntity));

    final var response = restaurantGateway.findByCnpj(cnpj);

    assertThat(response).isPresent();
    assertEquals(restaurantEntity.getId(), response.get().getId());
    assertEquals(restaurantEntity.getRestaurantName(), response.get().getRestaurantName());
    assertEquals(restaurantEntity.getCnpj(), response.get().getCnpj());
    assertEquals(restaurantEntity.getAddress(), response.get().getAddress());
    assertEquals(restaurantEntity.getCity(), response.get().getCity());
    assertEquals(restaurantEntity.getPhoneNumber(), response.get().getPhoneNumber());
    assertEquals(restaurantEntity.getTypeOfFood(), response.get().getTypeOfFood());
    assertEquals(restaurantEntity.getCapacity(), response.get().getCapacity());
    assertEquals(restaurantEntity.getNumberOfTables(), response.get().getNumberOfTables());
    assertEquals(restaurantEntity.getEmail(), response.get().getEmail());
    assertNotNull(response.get().getOpeningHour());
    assertEquals(1, response.get().getOpeningHour().size());

    final var responseOpeningHour = response.get().getOpeningHour().get(0);
    assertEquals(
        restaurantEntity.getOpeningHours().get(0).getDayOfWeek(),
        responseOpeningHour.getDayOfWeek());
    assertEquals(
        restaurantEntity.getOpeningHours().get(0).getOpenTime(), responseOpeningHour.getOpenTime());
    assertEquals(
        restaurantEntity.getOpeningHours().get(0).getCloseTime(),
        responseOpeningHour.getCloseTime());

    verify(restaurantRepository).findByCnpj(cnpj);
  }

  @Test
  void shouldReturnEmptyOptionalWhenNoRestaurantFoundByCnpj() {
    final var cnpj = "12345678901234";

    when(restaurantRepository.findByCnpj(cnpj)).thenReturn(Optional.empty());

    final var response = restaurantGateway.findByCnpj(cnpj);

    assertThat(response).isEmpty();

    verify(restaurantRepository).findByCnpj(cnpj);
  }

  @ParameterizedTest
  @MethodSource("provideRestaurantFilters")
  void shouldFindAllBySuccessfully(
      final String restaurantName, final String city, final String typeOfFood) {
    final var restaurantEntity = validRestaurantEntity();

    when(restaurantRepository.findAllBy(restaurantName, city, typeOfFood))
        .thenReturn(List.of(restaurantEntity));

    final var response = restaurantGateway.findAllBy(restaurantName, city, typeOfFood);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals(restaurantEntity.getId(), response.get(0).getId());
    assertEquals(restaurantEntity.getRestaurantName(), response.get(0).getRestaurantName());
    assertEquals(restaurantEntity.getCnpj(), response.get(0).getCnpj());
    assertEquals(restaurantEntity.getAddress(), response.get(0).getAddress());
    assertEquals(restaurantEntity.getCity(), response.get(0).getCity());
    assertEquals(restaurantEntity.getPhoneNumber(), response.get(0).getPhoneNumber());
    assertEquals(restaurantEntity.getTypeOfFood(), response.get(0).getTypeOfFood());
    assertEquals(restaurantEntity.getCapacity(), response.get(0).getCapacity());
    assertEquals(restaurantEntity.getNumberOfTables(), response.get(0).getNumberOfTables());
    assertEquals(restaurantEntity.getEmail(), response.get(0).getEmail());
    assertNotNull(response.get(0).getOpeningHour());
    assertEquals(1, response.get(0).getOpeningHour().size());

    final var responseOpeningHour = response.get(0).getOpeningHour().get(0);
    assertEquals(
        restaurantEntity.getOpeningHours().get(0).getDayOfWeek(),
        responseOpeningHour.getDayOfWeek());
    assertEquals(
        restaurantEntity.getOpeningHours().get(0).getOpenTime(), responseOpeningHour.getOpenTime());
    assertEquals(
        restaurantEntity.getOpeningHours().get(0).getCloseTime(),
        responseOpeningHour.getCloseTime());

    verify(restaurantRepository).findAllBy(restaurantName, city, typeOfFood);
  }

  @Test
  void shouldUpdateSuccessfully() {
    final var cnpj = "12345678901234";
    final var restaurant = validRestaurantDomain();
    final var restaurantEntity = validRestaurantEntity();
    final var updateRestaurant = updateRestaurantEntity();

    when(restaurantRepository.findByCnpj(cnpj)).thenReturn(Optional.of(restaurantEntity));
    when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(updateRestaurant);

    final var response = restaurantGateway.update(restaurant);

    assertNotNull(response);
    assertEquals(1, response.getId());
    assertEquals(restaurant.getRestaurantName(), response.getRestaurantName());
    assertEquals(restaurant.getCnpj(), response.getCnpj());
    assertEquals(updateRestaurant.getAddress(), response.getAddress());
    assertEquals(restaurant.getCity(), response.getCity());
    assertEquals(updateRestaurant.getPhoneNumber(), response.getPhoneNumber());
    assertEquals(updateRestaurant.getTypeOfFood(), response.getTypeOfFood());
    assertEquals(updateRestaurant.getCapacity(), response.getCapacity());
    assertEquals(updateRestaurant.getNumberOfTables(), response.getNumberOfTables());
    assertEquals(updateRestaurant.getEmail(), response.getEmail());
    assertNotNull(response.getOpeningHour());
    assertEquals(1, response.getOpeningHour().size());

    final var responseOpeningHour = response.getOpeningHour().get(0);
    assertEquals(
        restaurant.getOpeningHour().get(0).getDayOfWeek(), responseOpeningHour.getDayOfWeek());
    assertEquals(
        restaurant.getOpeningHour().get(0).getOpenTime(), responseOpeningHour.getOpenTime());
    assertEquals(
        restaurant.getOpeningHour().get(0).getCloseTime(), responseOpeningHour.getCloseTime());

    verify(restaurantRepository).findByCnpj(restaurant.getCnpj());
    verify(restaurantRepository).save(any(RestaurantEntity.class));
  }

  @Test
  void shouldThrowExceptionWhenUpdateRestaurantNotFound() {
    final var cnpj = "12345678901234";
    final var restaurant = validRestaurantDomain();

    when(restaurantRepository.findByCnpj(cnpj)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> restaurantGateway.update(restaurant))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Restaurant with cnpj=[12345678901234] not found.");

    verify(restaurantRepository).findByCnpj(restaurant.getCnpj());
  }

  @Test
  void shouldDeleteByCnpjSuccessfully() {
    final var cnpj = "12345678901234";

    doNothing().when(restaurantRepository).deleteByCnpj(cnpj);

    restaurantGateway.deleteByCnpj(cnpj);

    verify(restaurantRepository).deleteByCnpj(cnpj);
  }

  private static Stream<Arguments> provideRestaurantFilters() {
    return Stream.of(
        Arguments.of("Doce Sonho", "Natal", "Italian"),
        Arguments.of(null, "Natal", "Italian"),
        Arguments.of("Doce Sonho", null, "Italian"),
        Arguments.of("Doce Sonho", "Natal", null),
        Arguments.of(null, null, "Italian"),
        Arguments.of(null, "Natal", null),
        Arguments.of("Doce Sonho", null, null),
        Arguments.of(null, null, null));
  }
}
