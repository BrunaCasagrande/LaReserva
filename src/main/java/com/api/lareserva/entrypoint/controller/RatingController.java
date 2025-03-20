package com.api.lareserva.entrypoint.controller;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.usecase.CreateRating;
import com.api.lareserva.core.usecase.SearchRatingByRestaurant;
import com.api.lareserva.core.usecase.SearchRatingByUser;
import com.api.lareserva.presenter.RatingPresenter;
import com.api.lareserva.presenter.response.RatingPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/rating")
public class RatingController {

  private final CreateRating createRating;
  private final SearchRatingByRestaurant searchRatingByRestaurant;
  private final SearchRatingByUser searchRatingByUser;
  private final RatingPresenter ratingPresenter;

  @PostMapping
  public ResponseEntity<RatingPresenterResponse> create(@Valid @RequestBody final Rating request) {
    final var ratingCreated = this.createRating.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(ratingCreated.getId())
            .toUri();

    return ResponseEntity.created(location).body(ratingPresenter.parseToResponse(ratingCreated));
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<RatingPresenterResponse>> findByRestaurant(
      @PathVariable final Integer restaurantId) {
    final var ratings = this.searchRatingByRestaurant.execute(restaurantId);

    return ResponseEntity.ok(ratings.stream().map(ratingPresenter::parseToResponse).toList());
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RatingPresenterResponse>> findByUser(
      @PathVariable final Integer userId) {
    final var ratings = this.searchRatingByUser.execute(userId);

    return ResponseEntity.ok(ratings.stream().map(ratingPresenter::parseToResponse).toList());
  }
}
