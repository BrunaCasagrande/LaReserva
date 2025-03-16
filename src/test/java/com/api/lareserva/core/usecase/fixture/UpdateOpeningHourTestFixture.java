package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.core.dto.OpeningHourDto;
import java.time.LocalTime;

public class UpdateOpeningHourTestFixture {

  public static OpeningHour validExistingOpeningHour() {
    return OpeningHour.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.of(9, 0))
        .closeTime(LocalTime.of(18, 0))
        .build();
  }

  public static OpeningHourDto validUpdateRequest() {
    return OpeningHourDto.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.of(10, 0))
        .closeTime(LocalTime.of(19, 0))
        .build();
  }

  public static OpeningHour validUpdatedOpeningHour() {
    return OpeningHour.builder()
        .id(1)
        .dayOfWeek("Monday")
        .openTime(LocalTime.of(10, 0))
        .closeTime(LocalTime.of(19, 0))
        .build();
  }
}
