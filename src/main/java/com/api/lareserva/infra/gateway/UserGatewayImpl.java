package com.api.lareserva.infra.gateway;

import static java.lang.String.format;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.UserEntity;
import com.api.lareserva.infra.persistence.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

  private static final String FIND_ERROR_MESSAGE = "User with id=[%s] not found.";

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
  public Optional<User> findByCpf(final String cpf) {
    final var entity = userRepository.findByCpf(cpf);

    return entity.map(this::toResponse);
  }

  @Override
  public User update(final User user) {
    try {
      final var entity =
          userRepository
              .findById(user.getId())
              .orElseThrow(() -> new GatewayException(format(FIND_ERROR_MESSAGE, user.getId())));

      entity.setPhoneNumber(user.getPhoneNumber());
      entity.setEmail(user.getEmail());
      entity.setPassword(user.getPassword());

      final var updatedEntity = userRepository.save(entity);

      return this.toResponse(updatedEntity);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(FIND_ERROR_MESSAGE, user.getId()));
    }
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
