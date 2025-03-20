package com.api.lareserva.entrypoint.controller;

import static com.api.lareserva.entrypoint.controller.fixture.ReservationControllerTestFixture.EXISTING_RESERVATION_ID;
import static com.api.lareserva.entrypoint.controller.fixture.ReservationControllerTestFixture.updatedRequest;
import static com.api.lareserva.entrypoint.controller.fixture.ReservationControllerTestFixture.updatedResponse;
import static com.api.lareserva.entrypoint.controller.fixture.ReservationControllerTestFixture.validRequest;
import static com.api.lareserva.entrypoint.controller.fixture.ReservationControllerTestFixture.validResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.UpdateReservationDto;
import com.api.lareserva.core.usecase.CreateReservation;
import com.api.lareserva.core.usecase.DeleteReservation;
import com.api.lareserva.core.usecase.SearchReservation;
import com.api.lareserva.core.usecase.UpdateReservation;
import com.api.lareserva.presenter.ReservationPresenter;
import com.api.lareserva.presenter.response.ReservationPresenterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReservationControllerTest {

  private static final String BASE_URL = "/lareserva/reservation";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private CreateReservation createReservation;
  @MockBean private SearchReservation searchReservation;
  @MockBean private UpdateReservation updateReservation;
  @MockBean private DeleteReservation deleteReservation;
  @MockBean private ReservationPresenter reservationPresenter;

  @Test
  void shouldCreateAReservationSuccessfully() throws Exception {
    final var request = validRequest();
    final var createResponse = validResponse();

    when(createReservation.execute(any(Reservation.class))).thenReturn(createResponse);
    when(reservationPresenter.parseToResponse(createResponse))
        .thenReturn(mock(ReservationPresenterResponse.class));

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    final ArgumentCaptor<Reservation> reservationCaptor =
        ArgumentCaptor.forClass(Reservation.class);
    verify(createReservation).execute(reservationCaptor.capture());

    assertThat(reservationCaptor.getValue()).usingRecursiveComparison().isEqualTo(request);
  }

  @Test
  void shouldFindReservationByIdSuccessfully() throws Exception {
    final var response = validResponse();

    when(searchReservation.execute(EXISTING_RESERVATION_ID)).thenReturn(Optional.of(response));
    when(reservationPresenter.parseToResponse(response))
        .thenReturn(mock(ReservationPresenterResponse.class));

    mockMvc
        .perform(
            get(BASE_URL + "/{id}", EXISTING_RESERVATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(searchReservation).execute(EXISTING_RESERVATION_ID);
  }

  @Test
  void shouldReturnNotFoundWhenReservationDoesNotExist() throws Exception {
    when(searchReservation.execute(EXISTING_RESERVATION_ID)).thenReturn(Optional.empty());

    mockMvc
        .perform(
            get(BASE_URL + "/{id}", EXISTING_RESERVATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(searchReservation).execute(EXISTING_RESERVATION_ID);
  }

  @Test
  void shouldUpdateReservationSuccessfully() throws Exception {
    final var request = updatedRequest();
    final var response = updatedResponse();

    when(updateReservation.execute(eq(EXISTING_RESERVATION_ID), any(UpdateReservationDto.class)))
        .thenReturn(response);
    when(reservationPresenter.parseToResponse(response))
        .thenReturn(mock(ReservationPresenterResponse.class));

    mockMvc
        .perform(
            put(BASE_URL + "/{id}", EXISTING_RESERVATION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    verify(updateReservation).execute(eq(EXISTING_RESERVATION_ID), any(UpdateReservationDto.class));
  }

  @Test
  void shouldDeleteReservationSuccessfully() throws Exception {
    doNothing().when(deleteReservation).execute(EXISTING_RESERVATION_ID);

    mockMvc
        .perform(delete(BASE_URL + "/{id}", EXISTING_RESERVATION_ID))
        .andExpect(status().isNoContent());

    verify(deleteReservation).execute(EXISTING_RESERVATION_ID);
  }
}
