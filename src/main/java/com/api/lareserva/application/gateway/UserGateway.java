package com.api.lareserva.application.gateway;

import com.api.lareserva.application.domain.User;

public interface UserGateway {

    User save(final User user);
}
