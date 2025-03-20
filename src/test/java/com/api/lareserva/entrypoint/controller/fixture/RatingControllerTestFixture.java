package com.api.lareserva.entrypoint.controller.fixture;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import com.api.lareserva.presenter.response.RatingPresenterResponse;
import java.util.Date;

public class RatingControllerTestFixture {

  public static final Integer RATING_ID = 1;
  public static final Integer STARS = 5;
  public static final String COMMENT = "Excellent food and service!";
  public static final Date RATING_DATE = new Date();
  public static final Integer RESTAURANT_ID = 10;
  public static final Integer USER_ID = 5;

  public static Rating validRatingRequest() {
    return Rating.builder()
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(validRestaurant())
        .user(validUser())
        .build();
  }

  public static Rating validRatingResponse() {
    return Rating.builder()
        .id(RATING_ID)
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(validRestaurant())
        .user(validUser())
        .build();
  }

  public static RatingPresenterResponse validRatingPresenterResponse() {
    return RatingPresenterResponse.builder()
        .id(RATING_ID)
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurantId(RESTAURANT_ID)
        .userId(USER_ID)
        .build();
  }

  private static RestaurantDto validRestaurant() {
    return RestaurantDto.builder().id(RESTAURANT_ID).build();
  }

  private static UserDto validUser() {
    return UserDto.builder().id(USER_ID).build();
  }
}
