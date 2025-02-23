package com.api.lareserva.infrastructure.controller;

import com.api.lareserva.application.domain.User;
import com.api.lareserva.application.usecase.CreateUser;
import com.api.lareserva.presenter.UserPresenter;
import com.api.lareserva.presenter.response.UserPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/user")
public class UserController {

  private final CreateUser createUser;

  private final UserPresenter userPresenter;

  @PostMapping
  public ResponseEntity<UserPresenterResponse> create(@Valid @RequestBody final User request) {
    final var userCreated = this.createUser.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userCreated.getId())
            .toUri();

    return ResponseEntity.created(location).body(userPresenter.parseToResponse(userCreated));
  }
}
