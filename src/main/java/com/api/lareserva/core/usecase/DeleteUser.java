package com.api.lareserva.core.usecase;

import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUser {

  private final UserGateway userGateway;

  public void execute(final String cpf) {
    userGateway.findByCpf(cpf).orElseThrow(() -> new UserNotFoundException(cpf));
    userGateway.deleteByCpf(cpf);
  }
}
