package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.gateway.RestaurantGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchRestaurants {

  private final RestaurantGateway restaurantGateway;

  public List<Restaurant> execute(
      final String restaurantName, final String city, final String typeOfFood) {
    return this.restaurantGateway.findAllBy(restaurantName, city, typeOfFood);
  }
}
