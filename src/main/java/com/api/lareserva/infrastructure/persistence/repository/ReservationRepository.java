package com.api.lareserva.infrastructure.persistence.repository;

import com.api.lareserva.infrastructure.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {}
