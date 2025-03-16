package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.UpdateUserTestFixture.EXISTING_CPF;
import static com.api.lareserva.core.usecase.fixture.UpdateUserTestFixture.NONEXISTENT_CPF;
import static com.api.lareserva.core.usecase.fixture.UpdateUserTestFixture.existingUser;
import static com.api.lareserva.core.usecase.fixture.UpdateUserTestFixture.updateUserRequest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.dto.UpdateUserDto;
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
    final var updateRequest = updateUserRequest();
    final var expectedUpdatedUser =
        User.builder()
            .cpf(existingUser.getCpf())
            .name(updateRequest.getName())
            .phoneNumber(updateRequest.getPhoneNumber())
            .email(updateRequest.getEmail())
            .password(updateRequest.getPassword())
            .build();

    when(userGateway.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(existingUser));
    when(userGateway.update(any(User.class))).thenReturn(expectedUpdatedUser);

    final var result = updateUser.execute(EXISTING_CPF, updateRequest);

    assertThat(result).usingRecursiveComparison().isEqualTo(expectedUpdatedUser);

    verify(userGateway).findByCpf(EXISTING_CPF);
    final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userGateway).update(captor.capture());
    assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(expectedUpdatedUser);
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    final var updateRequest = updateUserRequest();

    when(userGateway.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateUser.execute(NONEXISTENT_CPF, updateRequest))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("User with CPF=[" + NONEXISTENT_CPF + "] not found.");

    verify(userGateway).findByCpf(NONEXISTENT_CPF);
    verify(userGateway, never()).update(any());
  }

  @Test
  void shouldUpdateUserPartially() {
    final var existingUser = existingUser();
    final var partialUpdateRequest = UpdateUserDto.builder().email("newemail@email.com").build();

    when(userGateway.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(existingUser));
    when(userGateway.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    final var result = updateUser.execute(EXISTING_CPF, partialUpdateRequest);

    assertThat(result.getCpf()).isEqualTo(existingUser.getCpf());
    assertThat(result.getName()).isEqualTo(existingUser.getName());
    assertThat(result.getPhoneNumber()).isEqualTo(existingUser.getPhoneNumber());
    assertThat(result.getEmail()).isEqualTo(partialUpdateRequest.getEmail());

    verify(userGateway).findByCpf(EXISTING_CPF);
    verify(userGateway).update(any());
  }
}
