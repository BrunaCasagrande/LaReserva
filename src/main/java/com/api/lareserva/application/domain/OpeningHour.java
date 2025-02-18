package com.api.lareserva.application.domain;

import com.api.lareserva.application.dto.RestaurantDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpeningHour {

  private Integer id;

  @NotBlank(message = "Day of week is required")
  private String dayOfWeek;

  @NotNull(message = "Open time is required")
  private LocalTime openTime;

  @NotNull(message = "Close time is required")
  private LocalTime closeTime;

  @NotNull(message = "Restaurant is required")
  private RestaurantDto restaurant;

  public static OpeningHour createOpeningHour(
      final String dayOfWeek,
      final LocalTime openTime,
      final LocalTime closeTime,
      final RestaurantDto restaurant) {
    return OpeningHour.builder()
        .dayOfWeek(dayOfWeek)
        .openTime(openTime)
        .closeTime(closeTime)
        .restaurant(restaurant)
        .build();
  }
}
