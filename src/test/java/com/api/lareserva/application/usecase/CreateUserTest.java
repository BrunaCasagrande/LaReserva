package com.api.lareserva.application.usecase;

import static com.api.lareserva.application.usecase.fixture.CreateUserTestFixture.validUserGatewayResponse;
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
    final var name = "Bruna Casagrande";
    final var cpf = "12345678900";
    final var phoneNumber = "11987654321";
    final var email = "bruna@email.com";
    final var password = "Bru123456!";
    final var user =
        User.builder()
            .name(name)
            .cpf(cpf)
            .phoneNumber(phoneNumber)
            .email(email)
            .password(password)
            .build();
    final var userGatewayResponse = validUserGatewayResponse();
    final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    when(userGateway.findByCpf(cpf)).thenReturn(empty());
    when(userGateway.save(userCaptor.capture())).thenReturn(userGatewayResponse);

    final var response = createUser.execute(user);

    assertThat(response.getId()).isEqualTo(1);
    assertThat(response.getName()).isEqualTo(name);
    assertThat(response.getCpf()).isEqualTo(cpf);
    assertThat(response.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(response.getEmail()).isEqualTo(email);
    assertThat(response.getPassword()).isEqualTo(password);

    verify(userGateway).findByCpf(cpf);
    verify(userGateway).save(userCaptor.getValue());
  }

  @Test
  void shouldNotCreateAUserWhenItAlreadyExists() {
    final var name = "Bruna Casagrande";
    final var cpf = "12345678900";
    final var phoneNumber = "11987654321";
    final var email = "bruna@email.com";
    final var password = "Bru123456!";
    final var user =
        User.builder()
            .name(name)
            .cpf(cpf)
            .phoneNumber(phoneNumber)
            .email(email)
            .password(password)
            .build();
    final var userGatewayResponse = validUserGatewayResponse();

    when(userGateway.findByCpf(cpf)).thenReturn(Optional.of(userGatewayResponse));

    assertThatThrownBy(() -> createUser.execute(user))
        .isInstanceOf(UserAlreadyExistsException.class)
        .hasMessage("User with cpf=[12345678900] already exists.");

    verify(userGateway).findByCpf(cpf);
    verify(userGateway, never()).save(any());
  }
}
