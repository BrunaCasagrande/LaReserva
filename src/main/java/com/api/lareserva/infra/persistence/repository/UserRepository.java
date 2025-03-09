package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.UserEntity;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByCpf(final String cpf);

  @Modifying
  @Transactional
  void deleteByCpf(String cpf);
}
