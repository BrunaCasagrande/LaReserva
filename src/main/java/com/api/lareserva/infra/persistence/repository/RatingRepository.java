package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.RatingEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

  List<RatingEntity> findByRestaurantId(final Integer restaurantId);

  List<RatingEntity> findByUserId(final Integer userId);
}
