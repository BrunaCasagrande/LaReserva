package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.OpeningHourEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHourRepository extends JpaRepository<OpeningHourEntity, Integer> {

  List<OpeningHourEntity> findByRestaurantId(final int restaurantId);
}
