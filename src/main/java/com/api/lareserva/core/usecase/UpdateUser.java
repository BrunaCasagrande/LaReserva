package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import com.api.lareserva.entrypoint.controller.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUser {
  private final UserGateway userGateway;

  public User execute(final String cpf, final UpdateUserRequest request) {
    final User existingUser =
        userGateway.findByCpf(cpf).orElseThrow(() -> new UserNotFoundException(cpf));

    existingUser.setName(request.getName());
    existingUser.setPhoneNumber(request.getPhoneNumber());
    existingUser.setEmail(request.getEmail());
    existingUser.setPassword(request.getPassword());

    return userGateway.update(existingUser);
  }
}
