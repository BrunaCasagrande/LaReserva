package com.api.lareserva.infrastructure.gateway;

import static com.api.lareserva.infrastructure.gateway.fixture.UserGatewayImplTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.api.lareserva.infrastructure.gateway.exception.GatewayException;
import com.api.lareserva.infrastructure.persistence.entity.UserEntity;
import com.api.lareserva.infrastructure.persistence.repository.UserRepository;
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
    assertThat(response.getId()).isEqualTo(entity.getId());
    assertThat(response.getName()).isEqualTo(captured.getName());
    assertThat(response.getCpf()).isEqualTo(captured.getCpf());
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
              assertThat(user.getId()).isEqualTo(entity.getId());
              assertThat(user.getName()).isEqualTo(entity.getName());
              assertThat(user.getCpf()).isEqualTo(entity.getCpf());
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

    when(userRepository.findById(existingEntity.getId())).thenReturn(Optional.of(existingEntity));
    when(userRepository.save(any(UserEntity.class))).thenReturn(updatedEntity);

    final var response = userGateway.update(updatedDomain);

    assertThat(response.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    assertThat(response.getEmail()).isEqualTo(UPDATED_EMAIL);

    verify(userRepository).findById(existingEntity.getId());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void shouldThrowAnExceptionWhenUpdatingNonExistentUser() {

    final var nonExistentUser = nonExistentUserDomain();

    when(userRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userGateway.update(nonExistentUser))
        .isInstanceOf(GatewayException.class)
        .hasMessage("User with id=[99] not found.");

    verify(userRepository).findById(NONEXISTENT_ID);
    verify(userRepository, never()).save(any());
  }

  @Test
  void shouldDeleteUserSuccessfully() {

    userGateway.deleteById(1);

    verify(userRepository).deleteById(1);
  }

  @Test
  void shouldReturnEmptyWhenUserIsNotFoundByCpf() {
    when(userRepository.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    final var response = userGateway.findByCpf(NONEXISTENT_CPF);

    assertThat(response).isEmpty();

    verify(userRepository).findByCpf(NONEXISTENT_CPF);
  }
}
