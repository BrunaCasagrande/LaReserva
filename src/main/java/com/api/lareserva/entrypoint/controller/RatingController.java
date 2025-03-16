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
import org.springframework.web.bind.annotation.*;
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
        ServletUriComponentsBuilder.fromPath("/lareserva/rating/{id}")
            .buildAndExpand(ratingCreated.getId())
            .toUri();

    return ResponseEntity.created(location).body(ratingPresenter.parseToResponse(ratingCreated));
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<RatingPresenterResponse>> findByRestaurant(
      @PathVariable final Integer restaurantId) {
    final var ratings = this.searchRatingByRestaurant.execute(restaurantId);

    if (ratings.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(ratings.stream().map(ratingPresenter::parseToResponse).toList());
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RatingPresenterResponse>> findByUser(
      @PathVariable final Integer userId) {
    final var ratings = this.searchRatingByUser.execute(userId);

    if (ratings.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(ratings.stream().map(ratingPresenter::parseToResponse).toList());
  }
}
