package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {}
