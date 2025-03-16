package com.api.lareserva.entrypoint.controller;

import com.api.lareserva.core.dto.OpeningHourDto;
import com.api.lareserva.core.usecase.UpdateOpeningHour;
import com.api.lareserva.presenter.OpeningHourPresenter;
import com.api.lareserva.presenter.response.OpeningHourPresenterResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/opening-hours")
public class OpeningHourController {

  private final UpdateOpeningHour updateOpeningHour;

  private final OpeningHourPresenter openingHourPresenter;

  @PutMapping("/{restaurantId}")
  public ResponseEntity<List<OpeningHourPresenterResponse>> update(
      @PathVariable final int restaurantId,
      @RequestBody @Valid final List<OpeningHourDto> request) {
    final var updatedOpeningHours = this.updateOpeningHour.execute(restaurantId, request);

    return ResponseEntity.ok(openingHourPresenter.parseToResponse(updatedOpeningHours));
  }
}
