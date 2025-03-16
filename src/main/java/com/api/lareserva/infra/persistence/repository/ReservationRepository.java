package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.ReservationEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

  List<ReservationEntity> findByRestaurantIdAndReservationDate(
      final Integer restaurantId, final Date reservationDate);
}
