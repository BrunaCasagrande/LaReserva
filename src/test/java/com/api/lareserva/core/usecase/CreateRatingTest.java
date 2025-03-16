package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.COMMENT;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.EXISTING_RATING_ID;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.RATING_DATE;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.RESTAURANT_ID;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.STARS;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.USER_ID;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.validRatingGatewayResponse;
import static com.api.lareserva.core.usecase.fixture.CreateRatingTestFixture.validRatingRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.gateway.RatingGateway;
import com.api.lareserva.core.usecase.exception.RatingAlreadyExistsException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreateRatingTest {

  private final RatingGateway ratingGateway = mock(RatingGateway.class);
  private final CreateRating createRating = new CreateRating(ratingGateway);

  @Test
  void shouldCreateARatingSuccessfully() {
    final var ratingRequest = validRatingRequest();
    final var ratingGatewayResponse = validRatingGatewayResponse();
    final ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);

    when(ratingGateway.save(ratingCaptor.capture())).thenReturn(ratingGatewayResponse);

    final var response = createRating.execute(ratingRequest);

    assertThat(response.getId()).isEqualTo(EXISTING_RATING_ID);
    assertThat(response.getStars()).isEqualTo(STARS);
    assertThat(response.getComment()).isEqualTo(COMMENT);
    assertThat(response.getDate()).isEqualTo(RATING_DATE);
    assertThat(response.getRestaurant().getId()).isEqualTo(RESTAURANT_ID);
    assertThat(response.getUser().getId()).isEqualTo(USER_ID);

    verify(ratingGateway).save(ratingCaptor.getValue());
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyRatedRestaurantToday() {
    final var ratingRequest = validRatingRequest();
    final var existingRating = validRatingGatewayResponse();

    when(ratingGateway.findByUser(USER_ID)).thenReturn(List.of(existingRating));

    assertThatThrownBy(() -> createRating.execute(ratingRequest))
        .isInstanceOf(RatingAlreadyExistsException.class)
        .hasMessageContaining(
            "User with ID=["
                + USER_ID
                + "] has already rated Restaurant with ID=["
                + RESTAURANT_ID
                + "] on date");

    verify(ratingGateway).findByUser(USER_ID);
  }
}
