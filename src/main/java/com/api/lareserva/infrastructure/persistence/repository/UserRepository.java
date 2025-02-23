package com.api.lareserva.infrastructure.persistence.repository;

import com.api.lareserva.infrastructure.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByCpf(final String cpf);
}
