package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUser {
  private final UserGateway userGateway;

  public User execute(final User user) {
    userGateway.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));

    return userGateway.update(user);
  }
}
