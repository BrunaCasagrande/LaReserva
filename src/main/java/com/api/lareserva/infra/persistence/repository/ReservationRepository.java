package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {}
