package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.gateway.RatingGateway;
import com.api.lareserva.core.usecase.exception.RatingAlreadyExistsException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRating {

  private final RatingGateway ratingGateway;

  public Rating execute(final Rating request) {

    final List<Rating> existingRatings = ratingGateway.findByUser(request.getUser().getId());

    final boolean alreadyRatedToday =
        existingRatings.stream()
            .anyMatch(
                rating ->
                    rating.getRestaurant().getId().equals(request.getRestaurant().getId())
                        && rating
                            .getDate()
                            .toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                            .equals(
                                request
                                    .getDate()
                                    .toInstant()
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .toLocalDate()));

    if (alreadyRatedToday) {
      throw new RatingAlreadyExistsException(
          request.getUser().getId(), request.getRestaurant().getId(), request.getDate());
    }

    return ratingGateway.save(request);
  }
}
