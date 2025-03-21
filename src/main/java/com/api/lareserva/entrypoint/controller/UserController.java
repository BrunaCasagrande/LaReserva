package com.api.lareserva.entrypoint.controller;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.dto.UpdateUserDto;
import com.api.lareserva.core.usecase.CreateUser;
import com.api.lareserva.core.usecase.DeleteUser;
import com.api.lareserva.core.usecase.SearchUser;
import com.api.lareserva.core.usecase.UpdateUser;
import com.api.lareserva.presenter.UserPresenter;
import com.api.lareserva.presenter.response.UserPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/user")
public class UserController {

  private final CreateUser createUser;
  private final DeleteUser deleteUser;
  private final SearchUser searchUser;
  private final UpdateUser updateUser;

  private final UserPresenter userPresenter;

  @PostMapping
  public ResponseEntity<UserPresenterResponse> create(@Valid @RequestBody final User request) {
    final var userCreated = this.createUser.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{cpf}")
            .buildAndExpand(userCreated.getCpf())
            .toUri();

    return ResponseEntity.created(location).body(userPresenter.parseToResponse(userCreated));
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<UserPresenterResponse> findByCpf(@PathVariable final String cpf) {
    return this.searchUser
        .execute(cpf)
        .map(user -> ResponseEntity.ok(userPresenter.parseToResponse(user)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{cpf}")
  public ResponseEntity<UserPresenterResponse> update(
      @PathVariable final String cpf, @Valid @RequestBody final UpdateUserDto request) {

    final var updatedUser = this.updateUser.execute(cpf, request);

    return ResponseEntity.ok(userPresenter.parseToResponse(updatedUser));
  }

  @DeleteMapping("/{cpf}")
  public ResponseEntity<Void> delete(@PathVariable final String cpf) {
    this.deleteUser.execute(cpf);
    return ResponseEntity.noContent().build();
  }
}
