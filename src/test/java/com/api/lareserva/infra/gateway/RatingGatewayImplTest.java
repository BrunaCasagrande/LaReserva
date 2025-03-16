package com.api.lareserva.infra.gateway;

import static com.api.lareserva.infra.gateway.fixture.RatingGatewayImplTestFixture.RATING_DATE;
import static com.api.lareserva.infra.gateway.fixture.RatingGatewayImplTestFixture.STARS;
import static com.api.lareserva.infra.gateway.fixture.RatingGatewayImplTestFixture.existingRatingEntity;
import static com.api.lareserva.infra.gateway.fixture.RatingGatewayImplTestFixture.newRating;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.infra.persistence.entity.RatingEntity;
import com.api.lareserva.infra.persistence.repository.RatingRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class RatingGatewayImplTest {

  private final RatingRepository ratingRepository = mock(RatingRepository.class);
  private final RatingGatewayImpl ratingGateway = new RatingGatewayImpl(ratingRepository);

  @Test
  void shouldSaveRatingSuccessfully() {
    final var entity = existingRatingEntity();
    final ArgumentCaptor<RatingEntity> captor = ArgumentCaptor.forClass(RatingEntity.class);
    final var domain = newRating();

    when(ratingRepository.save(captor.capture())).thenReturn(entity);

    final var response = ratingGateway.save(domain);

    final var captured = captor.getValue();
    assertThat(response.getId()).isEqualTo(entity.getId());
    assertThat(response.getStars()).isEqualTo(captured.getStars());
    assertThat(response.getComment()).isEqualTo(captured.getComment());
    assertThat(response.getDate()).isEqualTo(captured.getDate());

    verify(ratingRepository).save(captured);
  }

  @Test
  void shouldFindRatingsByRestaurantSuccessfully() {
    final var entity = existingRatingEntity();
    when(ratingRepository.findByRestaurantId(entity.getRestaurant().getId()))
        .thenReturn(List.of(entity));

    final var response = ratingGateway.findByRestaurant(entity.getRestaurant().getId());

    assertThat(response).hasSize(1);
    assertThat(response.get(0).getStars()).isEqualTo(STARS);
    assertThat(response.get(0).getDate()).isEqualTo(RATING_DATE);

    verify(ratingRepository).findByRestaurantId(entity.getRestaurant().getId());
  }

  @Test
  void shouldFindRatingsByUserSuccessfully() {
    final var entity = existingRatingEntity();
    when(ratingRepository.findByUserId(entity.getUser().getId())).thenReturn(List.of(entity));

    final var response = ratingGateway.findByUser(entity.getUser().getId());

    assertThat(response).hasSize(1);
    assertThat(response.get(0).getStars()).isEqualTo(STARS);
    assertThat(response.get(0).getDate()).isEqualTo(RATING_DATE);

    verify(ratingRepository).findByUserId(entity.getUser().getId());
  }
}
