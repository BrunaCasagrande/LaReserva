package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchUser {

  private final UserGateway userGateway;

  public Optional<User> execute(final String cpf) {
    return userGateway.findByCpf(cpf);
  }
}
