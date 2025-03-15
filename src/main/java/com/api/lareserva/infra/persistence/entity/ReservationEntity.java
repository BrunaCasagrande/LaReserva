package com.api.lareserva.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "reservation_date", nullable = false)
  private Date reservationDate;

  @Column(name = "reservation_time", nullable = false)
  private LocalTime reservationTime;

  @Column(name = "number_of_people", nullable = false)
  private Integer numberOfPeople;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private RestaurantEntity restaurant;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
}
