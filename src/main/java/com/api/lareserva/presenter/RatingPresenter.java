package com.api.lareserva.presenter;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.presenter.response.RatingPresenterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingPresenter {

  public RatingPresenterResponse parseToResponse(final Rating rating) {
    return RatingPresenterResponse.builder()
        .id(rating.getId())
        .stars(rating.getStars())
        .comment(rating.getComment())
        .date(rating.getDate())
        .restaurantId(rating.getRestaurant().getId())
        .userId(rating.getUser().getId())
        .build();
  }
}
