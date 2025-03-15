package com.api.lareserva.entrypoint.controller;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.usecase.CreateReservation;
import com.api.lareserva.core.usecase.DeleteReservation;
import com.api.lareserva.core.usecase.SearchReservation;
import com.api.lareserva.core.usecase.UpdateReservation;
import com.api.lareserva.entrypoint.controller.request.UpdateReservationRequest;
import com.api.lareserva.presenter.ReservationPresenter;
import com.api.lareserva.presenter.response.ReservationPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/reservation")
public class ReservationController {

  private final CreateReservation createReservation;
  private final DeleteReservation deleteReservation;
  private final SearchReservation searchReservation;
  private final UpdateReservation updateReservation;

  private final ReservationPresenter reservationPresenter;

  @PostMapping
  public ResponseEntity<ReservationPresenterResponse> create(
      @Valid @RequestBody final Reservation request) {
    final var reservationCreated = this.createReservation.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(reservationCreated.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(reservationPresenter.parseToResponse(reservationCreated));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationPresenterResponse> findById(@PathVariable final Integer id) {
    return this.searchReservation
        .execute(id)
        .map(reservation -> ResponseEntity.ok(reservationPresenter.parseToResponse(reservation)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationPresenterResponse> update(
      @PathVariable final Integer id, @Valid @RequestBody final UpdateReservationRequest request) {

    final var updatedReservation = this.updateReservation.execute(id, request);

    return ResponseEntity.ok(reservationPresenter.parseToResponse(updatedReservation));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable final Integer id) {
    this.deleteReservation.execute(id);
    return ResponseEntity.noContent().build();
  }
}
