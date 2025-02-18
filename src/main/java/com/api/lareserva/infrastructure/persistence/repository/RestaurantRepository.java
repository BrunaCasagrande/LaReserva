package com.api.lareserva.infrastructure.persistence.repository;

import com.api.lareserva.infrastructure.persistence.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {}
