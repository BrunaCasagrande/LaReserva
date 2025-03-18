package com.api.lareserva.entrypoint.controller;

import static com.api.lareserva.entrypoint.controller.fixture.RatingControllerTestFixture.RESTAURANT_ID;
import static com.api.lareserva.entrypoint.controller.fixture.RatingControllerTestFixture.USER_ID;
import static com.api.lareserva.entrypoint.controller.fixture.RatingControllerTestFixture.validRating;
import static com.api.lareserva.entrypoint.controller.fixture.RatingControllerTestFixture.validRatingPresenterResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.usecase.CreateRating;
import com.api.lareserva.core.usecase.SearchRatingByRestaurant;
import com.api.lareserva.core.usecase.SearchRatingByUser;
import com.api.lareserva.presenter.RatingPresenter;
import com.api.lareserva.presenter.response.RatingPresenterResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class RatingControllerTest {

  @Mock private CreateRating createRating;
  @Mock private SearchRatingByRestaurant searchRatingByRestaurant;
  @Mock private SearchRatingByUser searchRatingByUser;
  @Mock private RatingPresenter ratingPresenter;

  @InjectMocks private RatingController ratingController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateRatingSuccessfully() {
    final var rating = validRating();
    final var presenterResponse = validRatingPresenterResponse();

    when(createRating.execute(rating)).thenReturn(rating);
    when(ratingPresenter.parseToResponse(rating)).thenReturn(presenterResponse);

    final ResponseEntity<RatingPresenterResponse> response = ratingController.create(rating);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isEqualTo(presenterResponse);

    verify(createRating).execute(rating);
    verify(ratingPresenter).parseToResponse(rating);
  }

  @Test
  void shouldReturnRatingsForRestaurantSuccessfully() {
    final var ratings = List.of(validRating());
    final var presenterResponses = List.of(validRatingPresenterResponse());

    when(searchRatingByRestaurant.execute(RESTAURANT_ID)).thenReturn(ratings);
    when(ratingPresenter.parseToResponse(any())).thenReturn(presenterResponses.get(0));

    final ResponseEntity<List<RatingPresenterResponse>> response =
        ratingController.findByRestaurant(RESTAURANT_ID);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(presenterResponses);

    verify(searchRatingByRestaurant).execute(RESTAURANT_ID);
    verify(ratingPresenter, times(ratings.size())).parseToResponse(any());
  }

  @Test
  void shouldReturnNotFoundWhenNoRatingsForRestaurant() {
    when(searchRatingByRestaurant.execute(RESTAURANT_ID)).thenReturn(List.of());

    final ResponseEntity<List<RatingPresenterResponse>> response =
        ratingController.findByRestaurant(RESTAURANT_ID);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
    assertThat(response.getBody()).isNull();

    verify(searchRatingByRestaurant).execute(RESTAURANT_ID);
    verifyNoInteractions(ratingPresenter);
  }

  @Test
  void shouldReturnRatingsForUserSuccessfully() {
    final var ratings = List.of(validRating());
    final var presenterResponses = List.of(validRatingPresenterResponse());

    when(searchRatingByUser.execute(USER_ID)).thenReturn(ratings);
    when(ratingPresenter.parseToResponse(any())).thenReturn(presenterResponses.get(0));

    final ResponseEntity<List<RatingPresenterResponse>> response =
        ratingController.findByUser(USER_ID);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(presenterResponses);

    verify(searchRatingByUser).execute(USER_ID);
    verify(ratingPresenter, times(ratings.size())).parseToResponse(any());
  }

  @Test
  void shouldReturnNotFoundWhenNoRatingsForUser() {
    when(searchRatingByUser.execute(USER_ID)).thenReturn(List.of());

    final ResponseEntity<List<RatingPresenterResponse>> response =
        ratingController.findByUser(USER_ID);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
    assertThat(response.getBody()).isNull();

    verify(searchRatingByUser).execute(USER_ID);
    verifyNoInteractions(ratingPresenter);
  }
}
