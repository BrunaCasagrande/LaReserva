package com.api.lareserva.application.gateway;

import com.api.lareserva.application.domain.User;
import java.util.Optional;

public interface UserGateway {

  User save(final User user);

  Optional<User> findByCpf(final String cpf);

  User update(final User user);

  void deleteById(final int id);
}
