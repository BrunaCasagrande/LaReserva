package com.api.lareserva.entrypoint.controller.fixture;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.core.dto.OpeningHourDto;
import com.api.lareserva.presenter.response.OpeningHourPresenterResponse;
import java.time.LocalTime;

public class OpeningHourControllerTestFixture {

  public static OpeningHourDto validUpdateRequest() {
    return OpeningHourDto.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.parse("10:00"))
        .closeTime(LocalTime.parse("18:00"))
        .build();
  }

  public static OpeningHour validUpdateResponse() {
    return OpeningHour.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.parse("10:00"))
        .closeTime(LocalTime.parse("18:00"))
        .build();
  }

  public static OpeningHourPresenterResponse validUpdatePresenterResponse() {
    return OpeningHourPresenterResponse.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.parse("10:00"))
        .closeTime(LocalTime.parse("18:00"))
        .build();
  }
}
