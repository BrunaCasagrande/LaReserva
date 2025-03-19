package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.UpdateRestaurantDto;
import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.core.usecase.exception.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateRestaurant {

  private final RestaurantGateway restaurantGateway;

  public Restaurant execute(final String cnpj, final UpdateRestaurantDto request) {
    final var foundRestaurant =
        this.restaurantGateway
            .findByCnpj(cnpj)
            .orElseThrow(
                () ->
                    new RestaurantNotFoundException("Restaurant with CNPJ=[%s] not found.", cnpj));

    this.updatedRestaurantFields(foundRestaurant, request);

    if (request.password() != null && !request.password().isEmpty()) {
      final var updatedRestaurant =
          Restaurant.builder()
              .id(foundRestaurant.getId())
              .restaurantName(foundRestaurant.getRestaurantName())
              .cnpj(foundRestaurant.getCnpj())
              .address(foundRestaurant.getAddress())
              .city(foundRestaurant.getCity())
              .phoneNumber(foundRestaurant.getPhoneNumber())
              .typeOfFood(foundRestaurant.getTypeOfFood())
              .capacity(foundRestaurant.getCapacity())
              .numberOfTables(foundRestaurant.getNumberOfTables())
              .openingHour(foundRestaurant.getOpeningHour())
              .email(foundRestaurant.getEmail())
              .password(request.password())
              .build();

      log.info("The password is being updated.");
      return this.restaurantGateway.update(updatedRestaurant);
    }

    return this.restaurantGateway.update(foundRestaurant);
  }

  private void updatedRestaurantFields(
      final Restaurant foundRestaurant, final UpdateRestaurantDto request) {
    if (request.address() != null) foundRestaurant.setAddress(request.address());

    if (request.phoneNumber() != null) foundRestaurant.setPhoneNumber(request.phoneNumber());

    if (request.typeOfFood() != null) foundRestaurant.setTypeOfFood(request.typeOfFood());

    if (request.capacity() != null) foundRestaurant.setCapacity(request.capacity());

    if (request.numberOfTables() != null)
      foundRestaurant.setNumberOfTables(request.numberOfTables());

    if (request.email() != null) foundRestaurant.setEmail(request.email());
  }
}
