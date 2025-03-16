package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.core.usecase.exception.RestaurantAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRestaurant {

  private final RestaurantGateway restaurantGateway;

  public Restaurant execute(final Restaurant request) {
    final var restaurant = restaurantGateway.findByCnpj(request.getCnpj());

    if (restaurant.isPresent()) {
      throw new RestaurantAlreadyExistsException(request.getCnpj());
    }

    final var buildDomain =
        Restaurant.createRestaurant(
            request.getRestaurantName(),
            request.getCnpj(),
            request.getAddress(),
            request.getCity(),
            request.getPhoneNumber(),
            request.getTypeOfFood(),
            request.getCapacity(),
            request.getNumberOfTables(),
            request.getOpeningHour(),
            request.getEmail(),
            request.getPassword());

    return this.restaurantGateway.save(buildDomain);
  }
}
