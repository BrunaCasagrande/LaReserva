package com.api.lareserva.application.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {

  private Integer id;

  @NotNull(message = "Reservation Date is required")
  private Date reservationDate;

  @NotNull(message = "Reservation Time is required")
  private LocalTime reservationTime;

  @NotNull(message = "Number of Peoples is required")
  private Integer numberOfPeople;

  @NotNull(message = "Restaurant is required")
  private Restaurant restaurant;

  @NotNull(message = "User is required")
  private User user;

  public static Reservation createReservation(
      final Date reservationDate,
      final LocalTime reservationTime,
      final Integer numberOfPeople,
      final Restaurant restaurant,
      final User user) {
    return Reservation.builder()
        .reservationDate(reservationDate)
        .reservationTime(reservationTime)
        .restaurant(restaurant)
        .user(user)
        .build();
  }
}
