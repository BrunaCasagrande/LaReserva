package com.api.lareserva.presenter;

import com.api.lareserva.application.domain.User;
import com.api.lareserva.presenter.response.UserPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserPresenter {

  public UserPresenterResponse parseToResponse(final User user) {
    return UserPresenterResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .cpf(user.getCpf())
        .phoneNumber(user.getPhoneNumber())
        .email(user.getEmail())
        .build();
  }
}
