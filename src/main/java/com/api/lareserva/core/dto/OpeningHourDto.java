package com.api.lareserva.core.dto;

import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpeningHourDto {

  private Integer id;

  private String dayOfWeek;

  private LocalTime openTime;

  private LocalTime closeTime;
}
