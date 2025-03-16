package com.api.lareserva.infra.gateway;

import static com.api.lareserva.infra.gateway.fixture.UserGatewayImplTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.UserEntity;
import com.api.lareserva.infra.persistence.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UserGatewayImplTest {

  private final UserRepository userRepository = mock(UserRepository.class);
  private final UserGatewayImpl userGateway = new UserGatewayImpl(userRepository);

  @Test
  void shouldSaveUserSuccessfully() {
    final var entity = userEntity();
    final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
    final var domain = userDomain();

    when(userRepository.save(captor.capture())).thenReturn(entity);

    final var response = userGateway.save(domain);

    final var captured = captor.getValue();
    assertThat(response.getCpf()).isEqualTo(entity.getCpf());
    assertThat(response.getName()).isEqualTo(captured.getName());
    assertThat(response.getPhoneNumber()).isEqualTo(captured.getPhoneNumber());
    assertThat(response.getEmail()).isEqualTo(captured.getEmail());

    verify(userRepository).save(captured);
  }

  @Test
  void shouldFindUserByCpfSuccessfully() {
    final var entity = userEntity();
    when(userRepository.findByCpf(entity.getCpf())).thenReturn(Optional.of(entity));

    final var response = userGateway.findByCpf(entity.getCpf());

    assertThat(response).isPresent();
    assertThat(response)
        .hasValueSatisfying(
            user -> {
              assertThat(user.getCpf()).isEqualTo(entity.getCpf());
              assertThat(user.getName()).isEqualTo(entity.getName());
              assertThat(user.getPhoneNumber()).isEqualTo(entity.getPhoneNumber());
              assertThat(user.getEmail()).isEqualTo(entity.getEmail());
            });

    verify(userRepository).findByCpf(entity.getCpf());
  }

  @Test
  void shouldUpdateUserSuccessfully() {
    final var existingEntity = userEntity();
    final var updatedDomain = updatedUserDomain();
    final var updatedEntity = updatedUserEntity();

    when(userRepository.findByCpf(existingEntity.getCpf())).thenReturn(Optional.of(existingEntity));
    when(userRepository.save(any(UserEntity.class))).thenReturn(updatedEntity);

    final var response = userGateway.update(updatedDomain);

    assertThat(response.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    assertThat(response.getEmail()).isEqualTo(UPDATED_EMAIL);

    verify(userRepository).findByCpf(existingEntity.getCpf());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void shouldThrowAnExceptionWhenUpdatingNonExistentUser() {
    final var nonExistentUser = nonExistentUserDomain();

    when(userRepository.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userGateway.update(nonExistentUser))
        .isInstanceOf(GatewayException.class)
        .hasMessage("User with CPF=[99999999999] not found.");

    verify(userRepository).findByCpf(NONEXISTENT_CPF);
    verify(userRepository, never()).save(any());
  }

  @Test
  void shouldDeleteUserSuccessfully() {
    when(userRepository.findByCpf(CPF)).thenReturn(Optional.of(userEntity()));

    userGateway.deleteByCpf(CPF);

    verify(userRepository).deleteByCpf(CPF);
  }

  @Test
  void shouldReturnEmptyWhenUserIsNotFoundByCpf() {
    when(userRepository.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    final var response = userGateway.findByCpf(NONEXISTENT_CPF);

    assertThat(response).isEmpty();

    verify(userRepository).findByCpf(NONEXISTENT_CPF);
  }
}
