package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.UpdateUserTestFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UpdateUserTest {

  private final UserGateway userGateway = mock(UserGateway.class);
  private final UpdateUser updateUser = new UpdateUser(userGateway);

  @Test
  void shouldUpdateUserSuccessfully() {

    final var existingUser = existingUser();
    final var updatedUser = updatedUser();

    when(userGateway.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(existingUser));
    when(userGateway.update(any(User.class))).thenReturn(updatedUser);

    final var result = updateUser.execute(updatedUser);

    assertThat(result).usingRecursiveComparison().isEqualTo(updatedUser);

    verify(userGateway).findByCpf(EXISTING_CPF);
    final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userGateway).update(captor.capture());
    assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(updatedUser);
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {

    final var nonExistentUser = nonExistentUser();

    when(userGateway.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateUser.execute(nonExistentUser))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage(
            "User with CPF=[12345678900] not found."); // Ajuste a mensagem conforme necessário

    verify(userGateway).findByCpf(NONEXISTENT_CPF);
    verify(userGateway, never()).update(any());
  }
}
