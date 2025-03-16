package com.api.lareserva.presenter;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.presenter.response.OpeningHourPresenterResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OpeningHourPresenter {

  public List<OpeningHourPresenterResponse> parseToResponse(final List<OpeningHour> openingHours) {
    return openingHours.stream()
        .map(
            openingHour ->
                OpeningHourPresenterResponse.builder()
                    .id(openingHour.getId())
                    .dayOfWeek(openingHour.getDayOfWeek())
                    .openTime(openingHour.getOpenTime())
                    .closeTime(openingHour.getCloseTime())
                    .build())
        .toList();
  }
}
