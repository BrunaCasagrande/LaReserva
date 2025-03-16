package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.DeleteUserTestFixture.EXISTING_CPF;
import static com.api.lareserva.core.usecase.fixture.DeleteUserTestFixture.existingUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DeleteUserTest {

  private final UserGateway userGateway = mock(UserGateway.class);
  private final DeleteUser deleteUser = new DeleteUser(userGateway);

  @Test
  void shouldDeleteUserSuccessfully() {
    when(userGateway.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(existingUser()));
    doNothing().when(userGateway).deleteByCpf(EXISTING_CPF);

    deleteUser.execute(EXISTING_CPF);

    verify(userGateway).findByCpf(EXISTING_CPF);
    verify(userGateway).deleteByCpf(EXISTING_CPF);
  }

  @Test
  void shouldThrowExceptionWhenUserDoesNotExist() {
    when(userGateway.findByCpf(EXISTING_CPF)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> deleteUser.execute(EXISTING_CPF))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("User with CPF=[12345678900] not found.");

    verify(userGateway).findByCpf(EXISTING_CPF);
    verify(userGateway, never()).deleteByCpf(EXISTING_CPF);
  }
}
