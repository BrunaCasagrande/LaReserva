package com.api.lareserva.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpeningHourDto {

  private Integer id;

  private String dayOfWeek;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime openTime;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime closeTime;
}
