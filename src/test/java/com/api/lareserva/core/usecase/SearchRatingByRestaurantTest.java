package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.SearchRatingTestFixture.RESTAURANT_ID;
import static com.api.lareserva.core.usecase.fixture.SearchRatingTestFixture.validRatingsList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.RatingGateway;
import org.junit.jupiter.api.Test;

class SearchRatingByRestaurantTest {

  private final RatingGateway ratingGateway = mock(RatingGateway.class);
  private final SearchRatingByRestaurant searchRatingByRestaurant =
      new SearchRatingByRestaurant(ratingGateway);

  @Test
  void shouldReturnRatingsForRestaurantSuccessfully() {
    final var ratings = validRatingsList();

    when(ratingGateway.findByRestaurant(RESTAURANT_ID)).thenReturn(ratings);

    final var response = searchRatingByRestaurant.execute(RESTAURANT_ID);

    assertThat(response).isNotEmpty();
    assertThat(response.size()).isEqualTo(2);
    assertThat(response.get(0).getStars()).isEqualTo(5);
    assertThat(response.get(1).getStars()).isEqualTo(4);

    verify(ratingGateway).findByRestaurant(RESTAURANT_ID);
  }
}
