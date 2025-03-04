package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.DeleteUserTestFixture.EXISTING_ID;
import static com.api.lareserva.core.usecase.fixture.DeleteUserTestFixture.existingUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DeleteUserTest {

  private final UserGateway userGateway = mock(UserGateway.class);
  private final DeleteUser deleteUser = new DeleteUser(userGateway);

  @Test
  void shouldDeleteUserSuccessfully() {
    when(userGateway.findById(EXISTING_ID)).thenReturn(Optional.of(existingUser()));

    deleteUser.execute(EXISTING_ID);

    verify(userGateway).findById(EXISTING_ID);
    verify(userGateway).deleteById(EXISTING_ID);
  }

  @Test
  void shouldThrowExceptionWhenUserDoesNotExist() {
    when(userGateway.findById(EXISTING_ID)).thenReturn(Optional.empty());

    org.assertj.core.api.Assertions.assertThatThrownBy(() -> deleteUser.execute(EXISTING_ID))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("User with id=[1] not found.");

    verify(userGateway).findById(EXISTING_ID);
    verify(userGateway, never()).deleteById(anyInt());
  }
}
