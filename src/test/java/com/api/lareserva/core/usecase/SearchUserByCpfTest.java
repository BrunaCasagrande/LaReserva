package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.UserUseCaseFixture.NONEXISTENT_CPF;
import static com.api.lareserva.core.usecase.fixture.UserUseCaseFixture.validUserDomain;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.UserGateway;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchUserByCpfTest {

  @Mock private UserGateway userGateway;

  @InjectMocks private SearchUserByCpf searchUserByCpf;

  @Test
  void shouldFindUserSuccessfullyByCpf() {

    final var user = validUserDomain();
    when(userGateway.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

    final var result = searchUserByCpf.execute(user.getCpf());

    assertThat(result).usingRecursiveComparison().isEqualTo(user);
    verify(userGateway).findByCpf(user.getCpf());
  }

  @Test
  void shouldThrowExceptionWhenUserNotFoundByCpf() {

    when(userGateway.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> searchUserByCpf.execute(NONEXISTENT_CPF))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("User with cpf=[99999999999] not found.");

    verify(userGateway).findByCpf(NONEXISTENT_CPF);
  }
}
