package com.api.lareserva.entrypoint.controller;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.usecase.CreateUser;
import com.api.lareserva.core.usecase.DeleteUser;
import com.api.lareserva.core.usecase.SearchUserByCpf;
import com.api.lareserva.core.usecase.UpdateUser;
import com.api.lareserva.presenter.UserPresenter;
import com.api.lareserva.presenter.response.UserPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lareserva/user")
public class UserController {

  private final CreateUser createUser;
  private final DeleteUser deleteUser;
  private final SearchUserByCpf searchUserByCpf;
  private final UpdateUser updateUser;

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

  @GetMapping("/{cpf}")
  public ResponseEntity<UserPresenterResponse> findByCpf(@PathVariable final String cpf) {
    final var user = this.searchUserByCpf.execute(cpf);
    return ResponseEntity.ok(userPresenter.parseToResponse(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserPresenterResponse> update(
      @PathVariable final int id, @Valid @RequestBody final User request) {
    request.setId(id);
    final var updatedUser = this.updateUser.execute(request);
    return ResponseEntity.ok(userPresenter.parseToResponse(updatedUser));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable final int id) {
    this.deleteUser.execute(id);
    return ResponseEntity.noContent().build();
  }
}
