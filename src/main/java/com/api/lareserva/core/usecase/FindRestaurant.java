package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.gateway.RestaurantGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindRestaurant {

  private final RestaurantGateway restaurantGateway;

  public Optional<Restaurant> execute(final String cnpj) {
    return this.restaurantGateway.findByCnpj(cnpj);
  }
}
