package com.api.lareserva.entrypoint.controller;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.UpdateRestaurantDto;
import com.api.lareserva.core.usecase.*;
import com.api.lareserva.presenter.RestaurantPresenter;
import com.api.lareserva.presenter.response.RestaurantPresenterResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/restaurant")
public class RestaurantController {

  private final CreateRestaurant createRestaurant;
  private final FindRestaurant findRestaurant;
  private final SearchRestaurants searchRestaurants;
  private final UpdateRestaurant updateRestaurant;
  private final DeleteRestaurant deleteRestaurant;

  private final RestaurantPresenter restaurantPresenter;

  @PostMapping
  public ResponseEntity<RestaurantPresenterResponse> create(
      @Valid @RequestBody final Restaurant request) {
    final var restaurantCreated = this.createRestaurant.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(restaurantCreated.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(restaurantPresenter.parseToResponse(restaurantCreated));
  }

  @GetMapping("/{cnpj}")
  public ResponseEntity<RestaurantPresenterResponse> findByCnpj(
      @PathVariable @Pattern(regexp = "\\d{14}", message = "CNPJ must be 14 digits")
          final String cnpj) {
    final var restaurant = this.findRestaurant.execute(cnpj);

    return restaurant
        .map(value -> ResponseEntity.ok(restaurantPresenter.parseToResponse(value)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/search")
  public ResponseEntity<List<RestaurantPresenterResponse>> search(
      @RequestParam(required = false) final String restaurantName,
      @RequestParam(required = false) final String city,
      @RequestParam(required = false) final String typeOfFood) {
    final var restaurants = this.searchRestaurants.execute(restaurantName, city, typeOfFood);

    return ResponseEntity.ok(
        restaurants.stream().map(restaurantPresenter::parseToResponse).toList());
  }

  @PutMapping("/{cnpj}")
  public ResponseEntity<RestaurantPresenterResponse> update(
      @PathVariable @Pattern(regexp = "\\d{14}", message = "CNPJ must be 14 digits")
          final String cnpj,
      @Valid @RequestBody final UpdateRestaurantDto request) {
    final var updatedRestaurant = this.updateRestaurant.execute(cnpj, request);

    return ResponseEntity.ok(restaurantPresenter.parseToResponse(updatedRestaurant));
  }

  @DeleteMapping("/{cnpj}")
  public ResponseEntity<Void> delete(
      @PathVariable @Pattern(regexp = "\\d{14}", message = "CNPJ must be 14 digits")
          final String cnpj) {
    this.deleteRestaurant.execute(cnpj);

    return ResponseEntity.noContent().build();
  }
}
