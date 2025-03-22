package com.api.lareserva.entrypoint.controller;

import static com.api.lareserva.entrypoint.controller.fixture.RatingControllerTestFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.usecase.CreateRating;
import com.api.lareserva.core.usecase.SearchRatingByRestaurant;
import com.api.lareserva.core.usecase.SearchRatingByUser;
import com.api.lareserva.presenter.RatingPresenter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RatingControllerTest {

  private static final String BASE_URL = "/lareserva/rating";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private CreateRating createRating;
  @MockBean private SearchRatingByRestaurant searchRatingByRestaurant;
  @MockBean private SearchRatingByUser searchRatingByUser;
  @MockBean private RatingPresenter ratingPresenter;

  @Test
  void shouldCreateRatingSuccessfully() throws Exception {
    final var request = validRatingRequest();
    final var response = validRatingResponse();
    final var presenterResponse = validRatingPresenterResponse();

    when(createRating.execute(any(Rating.class))).thenReturn(response);
    when(ratingPresenter.parseToResponse(response)).thenReturn(presenterResponse);

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.stars").value(response.getStars()))
        .andExpect(jsonPath("$.comment").value(response.getComment()))
        .andExpect(jsonPath("$.restaurantId").value(response.getRestaurant().getId()))
        .andExpect(jsonPath("$.userId").value(response.getUser().getId()));
  }

  @Test
  void shouldReturnRatingsForRestaurantSuccessfully() throws Exception {
    final var response = List.of(validRatingResponse());
    final var presenterResponses = List.of(validRatingPresenterResponse());

    when(searchRatingByRestaurant.execute(RESTAURANT_ID)).thenReturn(response);
    when(ratingPresenter.parseToResponse(any(Rating.class))).thenReturn(presenterResponses.get(0));

    mockMvc
        .perform(
            get(BASE_URL + "/restaurant/{restaurantId}", RESTAURANT_ID)
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(response.get(0).getId()))
        .andExpect(jsonPath("$[0].stars").value(response.get(0).getStars()))
        .andExpect(jsonPath("$[0].comment").value(response.get(0).getComment()))
        .andExpect(jsonPath("$[0].restaurantId").value(response.get(0).getRestaurant().getId()))
        .andExpect(jsonPath("$[0].userId").value(response.get(0).getUser().getId()));
  }

  @Test
  void shouldReturnNotFoundWhenNoRatingsForRestaurant() throws Exception {
    when(searchRatingByRestaurant.execute(RESTAURANT_ID)).thenReturn(List.of());

    mockMvc
        .perform(
            get(BASE_URL + "/restaurant/{restaurantId}", RESTAURANT_ID)
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void shouldReturnRatingsForUserSuccessfully() throws Exception {
    final var response = List.of(validRatingResponse());
    final var presenterResponses = List.of(validRatingPresenterResponse());

    when(searchRatingByUser.execute(USER_ID)).thenReturn(response);
    when(ratingPresenter.parseToResponse(any(Rating.class))).thenReturn(presenterResponses.get(0));

    mockMvc
        .perform(get(BASE_URL + "/user/{userId}", USER_ID).contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(response.get(0).getId()))
        .andExpect(jsonPath("$[0].stars").value(response.get(0).getStars()))
        .andExpect(jsonPath("$[0].comment").value(response.get(0).getComment()))
        .andExpect(jsonPath("$[0].restaurantId").value(response.get(0).getRestaurant().getId()))
        .andExpect(jsonPath("$[0].userId").value(response.get(0).getUser().getId()));
  }

  @Test
  void shouldReturnNotFoundWhenNoRatingsForUser() throws Exception {
    when(searchRatingByUser.execute(USER_ID)).thenReturn(List.of());

    mockMvc
        .perform(get(BASE_URL + "/user/{userId}", USER_ID).contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }
}
