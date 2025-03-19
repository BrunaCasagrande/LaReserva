package com.api.lareserva.core.gateway;

import com.api.lareserva.core.domain.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {

  Restaurant save(final Restaurant restaurant);

  Optional<Restaurant> findByCnpj(final String cnpj);

  List<Restaurant> findAllBy(
      final String restaurantName, final String city, final String typeOfFood);

  Restaurant update(final Restaurant restaurant);

  void deleteByCnpj(final String cnpj);
}
