package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.gateway.RatingGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchRatingByRestaurant {

  private final RatingGateway ratingGateway;

  public List<Rating> execute(final Integer restaurantId) {
    return ratingGateway.findByRestaurant(restaurantId);
  }
}
