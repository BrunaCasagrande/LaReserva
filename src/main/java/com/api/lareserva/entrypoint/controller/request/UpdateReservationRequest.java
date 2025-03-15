package com.api.lareserva.entrypoint.controller.request;

import java.time.LocalTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReservationRequest {

  private Date reservationDate;

  private LocalTime reservationTime;

  private Integer numberOfPeople;
}
