package com.api.lareserva.application.usecase;

import static com.api.lareserva.application.usecase.fixture.CreateUserTestFixture.*;
import static com.api.lareserva.infrastructure.controller.fixture.UserControllerTestFixture.validRequest;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import com.api.lareserva.application.domain.User;
import com.api.lareserva.application.gateway.UserGateway;
import com.api.lareserva.application.usecase.exception.UserAlreadyExistsException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreateUserTest {

  private final UserGateway userGateway = mock(UserGateway.class);
  private final CreateUser createUser = new CreateUser(userGateway);

  @Test
  void shouldCreateAUserSuccessfully() {
    final var user = validUserRequest();
    final var userGatewayResponse = validUserGatewayResponse();
    final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    when(userGateway.findByCpf(CPF)).thenReturn(empty());
    when(userGateway.save(userCaptor.capture())).thenReturn(userGatewayResponse);

    final var response = createUser.execute(user);

    assertThat(response.getId()).isEqualTo(1);
    assertThat(response.getName()).isEqualTo(NAME);
    assertThat(response.getCpf()).isEqualTo(CPF);
    assertThat(response.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
    assertThat(response.getEmail()).isEqualTo(EMAIL);
    assertThat(response.getPassword()).isEqualTo(PASSWORD);

    verify(userGateway).findByCpf(CPF);
    verify(userGateway).save(userCaptor.getValue());
  }

  @Test
  void shouldNotCreateAUserWhenItAlreadyExists() {
    final var user = validRequest();
    final var userGatewayResponse = validUserGatewayResponse();

    when(userGateway.findByCpf(user.getCpf())).thenReturn(Optional.of(userGatewayResponse));

    assertThatThrownBy(() -> createUser.execute(user))
        .isInstanceOf(UserAlreadyExistsException.class)
        .hasMessage("User with cpf=[12345678900] already exists.");

    verify(userGateway).findByCpf(user.getCpf());
    verify(userGateway, never()).save(any());
  }
}
