package com.api.lareserva.core.dto;

import java.time.LocalTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReservationDto {

  private Date reservationDate;

  private LocalTime reservationTime;

  private Integer numberOfPeople;
}
