package com.api.lareserva.infra.gateway;

import static java.lang.String.format;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.OpeningHourDto;
import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.OpeningHourEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.repository.OpeningHourRepository;
import com.api.lareserva.infra.persistence.repository.RestaurantRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantGatewayImpl implements RestaurantGateway {

  private static final String FIND_ERROR_MESSAGE = "Restaurant with cnpj=[%s] not found.";

  private final OpeningHourRepository openingHourRepository;
  private final RestaurantRepository restaurantRepository;

  @Override
  public Restaurant save(final Restaurant restaurant) {
    final var entity =
        RestaurantEntity.builder()
            .restaurantName(restaurant.getRestaurantName())
            .cnpj(restaurant.getCnpj())
            .address(restaurant.getAddress())
            .city(restaurant.getCity())
            .phoneNumber(restaurant.getPhoneNumber())
            .typeOfFood(restaurant.getTypeOfFood())
            .capacity(restaurant.getCapacity())
            .numberOfTables(restaurant.getNumberOfTables())
            .email(restaurant.getEmail())
            .password(restaurant.getPassword())
            .build();

    final var savedRestaurant = restaurantRepository.save(entity);
    final var openingHours = toOpeningHourEntity(restaurant.getOpeningHour(), savedRestaurant);

    openingHourRepository.saveAll(openingHours);

    savedRestaurant.setOpeningHours(openingHours);

    return this.toResponse(savedRestaurant);
  }

  @Override
  public Optional<Restaurant> findByCnpj(final String cnpj) {
    final var entity = restaurantRepository.findByCnpj(cnpj);

    return entity.map(this::toResponse);
  }

  @Override
  public List<Restaurant> findAllBy(
      final String restaurantName, final String city, final String typeOfFood) {
    final var entities = restaurantRepository.findAllBy(restaurantName, city, typeOfFood);

    return entities.stream().map(this::toResponse).toList();
  }

  @Override
  public Restaurant update(final Restaurant restaurant) {
    try {
      final var entity =
          restaurantRepository
              .findByCnpj(restaurant.getCnpj())
              .orElseThrow(
                  () -> new GatewayException(format(FIND_ERROR_MESSAGE, restaurant.getCnpj())));

      entity.setAddress(restaurant.getAddress());
      entity.setPhoneNumber(restaurant.getPhoneNumber());
      entity.setTypeOfFood(restaurant.getTypeOfFood());
      entity.setCapacity(restaurant.getCapacity());
      entity.setNumberOfTables(restaurant.getNumberOfTables());
      entity.setEmail(restaurant.getEmail());

      if (restaurant.getPassword() != null) {
        entity.setPassword(restaurant.getPassword());
        log.info("Password updated.");
      }

      final var updatedEntity = restaurantRepository.save(entity);

      return this.toResponse(updatedEntity);

    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(FIND_ERROR_MESSAGE, restaurant.getCnpj()));
    }
  }

  @Override
  public void deleteByCnpj(final String cnpj) {
    restaurantRepository.deleteByCnpj(cnpj);
  }

  private List<OpeningHourEntity> toOpeningHourEntity(
      final List<OpeningHourDto> openingHours, final RestaurantEntity restaurant) {
    return openingHours.stream()
        .map(
            openingHour ->
                OpeningHourEntity.builder()
                    .dayOfWeek(openingHour.getDayOfWeek())
                    .openTime(openingHour.getOpenTime())
                    .closeTime(openingHour.getCloseTime())
                    .restaurant(restaurant)
                    .build())
        .toList();
  }

  private List<OpeningHourDto> toOpeningHourDto(final List<OpeningHourEntity> openingHours) {
    return openingHours.stream()
        .map(
            openingHour ->
                OpeningHourDto.builder()
                    .id(openingHour.getId())
                    .dayOfWeek(openingHour.getDayOfWeek())
                    .openTime(openingHour.getOpenTime())
                    .closeTime(openingHour.getCloseTime())
                    .build())
        .toList();
  }

  private Restaurant toResponse(final RestaurantEntity entity) {
    return Restaurant.builder()
        .id(entity.getId())
        .restaurantName(entity.getRestaurantName())
        .cnpj(entity.getCnpj())
        .address(entity.getAddress())
        .city(entity.getCity())
        .phoneNumber(entity.getPhoneNumber())
        .typeOfFood(entity.getTypeOfFood())
        .capacity(entity.getCapacity())
        .numberOfTables(entity.getNumberOfTables())
        .openingHour(toOpeningHourDto(entity.getOpeningHours()))
        .email(entity.getEmail())
        .build();
  }
}
