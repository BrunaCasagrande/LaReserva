package com.api.lareserva.infrastructure.persistence.repository;

import com.api.lareserva.infrastructure.persistence.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {}
