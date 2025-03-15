package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.ReservationEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

  Optional<ReservationEntity> findById(final Integer id);

  List<ReservationEntity> findByRestaurantIdAndReservationDate(
      final Integer restaurantId, final Date reservationDate);

  void deleteById(final Integer id);
}
