package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchUserByCpf {

  private final UserGateway userGateway;

  public User execute(final String cpf) {
    return userGateway.findByCpf(cpf).orElseThrow(() -> new UserNotFoundException(cpf));
  }
}
