package com.api.lareserva.core.gateway;

import com.api.lareserva.core.domain.Rating;
import java.util.List;

public interface RatingGateway {

  Rating save(final Rating rating);

  List<Rating> findByRestaurant(final Integer restaurantId);

  List<Rating> findByUser(final Integer userId);
}
