package com.api.lareserva.infrastructure.controller;

import static com.api.lareserva.infrastructure.controller.fixture.UserControllerTestFixture.validRequest;
import static com.api.lareserva.infrastructure.controller.fixture.UserControllerTestFixture.validResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.lareserva.application.domain.User;
import com.api.lareserva.application.usecase.CreateUser;
import com.api.lareserva.application.usecase.exception.UserAlreadyExistsException;
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
}
