package com.api.lareserva.infrastructure.gateway;

import com.api.lareserva.application.domain.User;
import com.api.lareserva.application.gateway.UserGateway;
import com.api.lareserva.application.usecase.exception.UserNotFoundException;
import com.api.lareserva.infrastructure.persistence.entity.UserEntity;
import com.api.lareserva.infrastructure.persistence.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

  private final UserRepository userRepository;

  @Override
  public User save(final User user) {
    final var entity =
        UserEntity.builder()
            .name(user.getName())
            .cpf(user.getCpf())
            .phoneNumber(user.getPhoneNumber())
            .email(user.getEmail())
            .password(user.getPassword())
            .build();

    final var saved = userRepository.save(entity);

    return this.toResponse(saved);
  }

  @Override
  public Optional<User> findById(final int id) {
    final var entity = userRepository.findById(id);

    return entity.map(this::toResponse);
  }

  @Override
  public User update(final User user) {
    final var entity =
        userRepository
            .findById(user.getId())
            .orElseThrow(() -> new UserNotFoundException(user.getId()));

    entity.setPhoneNumber(user.getPhoneNumber());
    entity.setEmail(user.getEmail());
    entity.setPassword(user.getPassword());

    final var updatedEntity = userRepository.save(entity);

    return this.toResponse(updatedEntity);
  }

  @Override
  public void deleteById(final int id) {
    userRepository.deleteById(id);
  }

  private User toResponse(final UserEntity entity) {
    return User.builder()
        .id(entity.getId())
        .name(entity.getName())
        .cpf(entity.getCpf())
        .phoneNumber(entity.getPhoneNumber())
        .email(entity.getEmail())
        .build();
  }
}
