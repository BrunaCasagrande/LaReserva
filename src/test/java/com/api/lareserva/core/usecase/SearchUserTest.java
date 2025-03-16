package com.api.lareserva.core.usecase;

import static com.api.lareserva.core.usecase.fixture.SearchUserTestFixture.NONEXISTENT_CPF;
import static com.api.lareserva.core.usecase.fixture.SearchUserTestFixture.validUserDomain;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.lareserva.core.gateway.UserGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchUserTest {

  private final UserGateway userGateway = mock(UserGateway.class);

  private final SearchUser searchUser = new SearchUser(userGateway);

  @Test
  void shouldFindUserSuccessfullyByCpf() {

    final var user = validUserDomain();
    when(userGateway.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

    final var result = searchUser.execute(user.getCpf());

    assertThat(result).isPresent();
    assertThat(result).hasValue(user);

    verify(userGateway).findByCpf(user.getCpf());
  }

  @Test
  void shouldThrowExceptionWhenUserNotFoundByCpf() {

    when(userGateway.findByCpf(NONEXISTENT_CPF)).thenReturn(Optional.empty());

    final var response = searchUser.execute(NONEXISTENT_CPF);

    assertThat(response).isEmpty();

    verify(userGateway).findByCpf(NONEXISTENT_CPF);
  }
}
