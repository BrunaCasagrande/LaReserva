package com.api.lareserva.entrypoint.controller;

import static com.api.lareserva.entrypoint.controller.fixture.OpeningHourControllerTestFixture.validUpdatePresenterResponse;
import static com.api.lareserva.entrypoint.controller.fixture.OpeningHourControllerTestFixture.validUpdateRequest;
import static com.api.lareserva.entrypoint.controller.fixture.OpeningHourControllerTestFixture.validUpdateResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.lareserva.core.usecase.UpdateOpeningHour;
import com.api.lareserva.presenter.OpeningHourPresenter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
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
class OpeningHourControllerTest {

  private static final String BASE_URL = "/lareserva/opening-hours";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private UpdateOpeningHour updateOpeningHour;
  @MockBean private OpeningHourPresenter openingHourPresenter;

  @Test
  void shouldUpdateOpeningHoursSuccessfully() throws Exception {
    final var request = List.of(validUpdateRequest());
    final var updateResponse = List.of(validUpdateResponse());
    final var presenterResponse = List.of(validUpdatePresenterResponse());

    when(updateOpeningHour.execute(1, request)).thenReturn(updateResponse);
    when(openingHourPresenter.parseToResponse(anyList())).thenReturn(presenterResponse);

    mockMvc
        .perform(
            put(BASE_URL + "/{restaurantId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(presenterResponse.get(0).id()))
        .andExpect(jsonPath("$[0].dayOfWeek").value(presenterResponse.get(0).dayOfWeek()))
        .andExpect(
            jsonPath("$[0].openTime", equalTo(presenterResponse.get(0).openTime().toString())))
        .andExpect(
            jsonPath("$[0].closeTime", equalTo(presenterResponse.get(0).closeTime().toString())));
  }
}
