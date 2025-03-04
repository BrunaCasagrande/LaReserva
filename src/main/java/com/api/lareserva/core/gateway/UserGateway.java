package com.api.lareserva.core.gateway;

import com.api.lareserva.core.domain.User;
import java.util.Optional;

public interface UserGateway {

  User save(final User user);

  Optional<User> findByCpf(final String cpf);

  User update(final User user);

  void deleteById(final int id);

  Optional<User> findById(final int id);
}
