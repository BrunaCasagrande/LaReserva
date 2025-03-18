package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.SearchRatingTestFixture.USER_ID;
import static com.api.lareserva.core.usecase.fixture.SearchRatingTestFixture.validRatingsList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.RatingGateway;
import org.junit.jupiter.api.Test;

class SearchRatingByUserTest {

  private final RatingGateway ratingGateway = mock(RatingGateway.class);
  private final SearchRatingByUser searchRatingByUser = new SearchRatingByUser(ratingGateway);

  @Test
  void shouldReturnRatingsForUserSuccessfully() {
    final var ratings = validRatingsList();

    when(ratingGateway.findByUser(USER_ID)).thenReturn(ratings);

    final var response = searchRatingByUser.execute(USER_ID);

    assertThat(response).isNotEmpty();
    assertThat(response.size()).isEqualTo(2);
    assertThat(response.get(0).getStars()).isEqualTo(5);
    assertThat(response.get(1).getStars()).isEqualTo(4);

    verify(ratingGateway).findByUser(USER_ID);
  }
}
