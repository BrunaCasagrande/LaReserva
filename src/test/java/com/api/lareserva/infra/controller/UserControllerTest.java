package com.api.lareserva.infra.controller;

import static com.api.lareserva.infra.controller.fixture.UserControllerTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.lareserva.core.domain.User;
import com.api.lareserva.core.usecase.CreateUser;
import com.api.lareserva.core.usecase.DeleteUser;
import com.api.lareserva.core.usecase.SearchUserByCpf;
import com.api.lareserva.core.usecase.UpdateUser;
import com.api.lareserva.core.usecase.exception.UserAlreadyExistsException;
import com.api.lareserva.core.usecase.exception.UserNotFoundException;
import com.api.lareserva.infra.controller.fixture.UserControllerTestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  private static final String BASE_URL = "/lareserva/user";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private CreateUser createUser;
  @MockBean private SearchUserByCpf searchUserByCpf;
  @MockBean private UpdateUser updateUser;
  @MockBean private DeleteUser deleteUser;

  @Test
  void shouldCreateAUserSuccessfully() throws Exception {
    final var request = validRequest();
    final var createResponse = validResponse();

    when(createUser.execute(any(User.class))).thenReturn(createResponse);

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(createResponse.getId()))
        .andExpect(jsonPath("$.name").value(createResponse.getName()))
        .andExpect(jsonPath("$.cpf").value(createResponse.getCpf()))
        .andExpect(jsonPath("$.phoneNumber").value(createResponse.getPhoneNumber()))
        .andExpect(jsonPath("$.email").value(createResponse.getEmail()));
    final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(createUser).execute(userCaptor.capture());

    assertThat(userCaptor.getValue()).usingRecursiveComparison().isEqualTo(request);
  }

  @Test
  void shouldThrowAnExceptionWhenCpfAlreadyExists() throws Exception {

    final var request = validRequest();

    when(createUser.execute(any(User.class)))
        .thenThrow(new UserAlreadyExistsException(request.getCpf()));

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("User with cpf=[12345678900] already exists."));
  }

  @Test
  void shouldFindUserByCpfSuccessfully() throws Exception {

    final var cpf = UserControllerTestFixture.CPF;
    final var response = validResponse();

    when(searchUserByCpf.execute(cpf)).thenReturn(response);

    mockMvc
        .perform(get(BASE_URL + "/{cpf}", cpf).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.cpf").value(response.getCpf()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));

    verify(searchUserByCpf).execute(cpf);
  }

  @Test
  void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {

    final var cpf = UserControllerTestFixture.CPF;

    when(searchUserByCpf.execute(cpf)).thenThrow(new UserNotFoundException(cpf));

    mockMvc
        .perform(get(BASE_URL + "/{cpf}", cpf).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("User with cpf=[12345678900] not found."));

    verify(searchUserByCpf).execute(cpf);
  }

  @Test
  void shouldUpdateUserSuccessfully() throws Exception {
    final var request = validRequest();
    final var response = validResponse();

    when(updateUser.execute(any(User.class))).thenReturn(response);

    mockMvc
        .perform(
            put(BASE_URL + "/{id}", response.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.cpf").value(response.getCpf()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));

    verify(updateUser).execute(any(User.class));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentUser() throws Exception {
    final var request = validRequestWithId();

    when(updateUser.execute(any(User.class))).thenThrow(new UserNotFoundException(request.getId()));

    mockMvc
        .perform(
            put(BASE_URL + "/{id}", request.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("User with id=[1] not found."));

    verify(updateUser).execute(any(User.class));
  }

  @Test
  void shouldDeleteUserSuccessfully() throws Exception {
    doNothing().when(deleteUser).execute(ID);

    mockMvc.perform(delete(BASE_URL + "/{id}", ID)).andExpect(status().isNoContent());

    verify(deleteUser).execute(ID);
  }

  @Test
  void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
    doThrow(new UserNotFoundException(ID)).when(deleteUser).execute(ID);

    mockMvc
        .perform(delete(BASE_URL + "/{id}", ID))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("User with id=[1] not found."));

    verify(deleteUser).execute(ID);
  }
}
