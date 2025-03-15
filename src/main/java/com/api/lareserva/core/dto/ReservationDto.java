package com.api.lareserva.core.dto;

import java.time.LocalTime;
import java.util.Date;
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
public class ReservationDto {

  private Integer id;

  private Date reservationDate;

  private LocalTime reservationTime;

  private Integer numberOfPeople;

  private RestaurantDto restaurant;

  private UserDto user;
}
