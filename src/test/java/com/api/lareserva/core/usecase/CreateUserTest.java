package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.CPF;
import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.EMAIL;
import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.NAME;
import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.PASSWORD;
import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.PHONE_NUMBER;
import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.validUserGatewayResponse;
import static com.api.lareserva.core.usecase.fixture.CreateUserTestFixture.validUserRequest;
import static com.api.lareserva.entrypoint.controller.fixture.UserControllerTestFixture.validRequest;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserAlreadyExistsException;
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
