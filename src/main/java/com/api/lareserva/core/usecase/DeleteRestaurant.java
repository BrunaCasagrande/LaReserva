package com.api.lareserva.core.usecase;

import com.api.lareserva.core.gateway.RestaurantGateway;
import com.api.lareserva.core.usecase.exception.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteRestaurant {

  private final RestaurantGateway restaurantGateway;

  @Transactional
  public void execute(final String cnpj) {
    this.restaurantGateway
        .findByCnpj(cnpj)
        .orElseThrow(
            () -> new RestaurantNotFoundException("Restaurant with CNPJ=[%s] not found.", cnpj));

    this.restaurantGateway.deleteByCnpj(cnpj);
  }
}
