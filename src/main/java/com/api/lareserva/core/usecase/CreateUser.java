package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUser {

  private final UserGateway userGateway;

  public User execute(final User request) {
    final var user = userGateway.findByCpf(request.getCpf());

    if (user.isPresent()) {
      throw new UserAlreadyExistsException(request.getCpf());
    }

    final var buildDomain =
        User.createUser(
            request.getName(),
            request.getCpf(),
            request.getPhoneNumber(),
            request.getEmail(),
            request.getPassword());

    return userGateway.save(buildDomain);
  }
}
