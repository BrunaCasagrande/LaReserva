package com.api.lareserva.entrypoint.controller;

import static com.api.lareserva.entrypoint.controller.fixture.RestaurantControllerTestFixture.validRestaurantPresenterResponse;
import static com.api.lareserva.entrypoint.controller.fixture.RestaurantControllerTestFixture.validRestaurantRequest;
import static com.api.lareserva.entrypoint.controller.fixture.RestaurantControllerTestFixture.validRestaurantResponse;
import static com.api.lareserva.entrypoint.controller.fixture.RestaurantControllerTestFixture.validUpdateRestaurantPresenterResponse;
import static com.api.lareserva.entrypoint.controller.fixture.RestaurantControllerTestFixture.validUpdateRestaurantRequest;
import static com.api.lareserva.entrypoint.controller.fixture.RestaurantControllerTestFixture.validUpdateRestaurantResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.UpdateRestaurantDto;
import com.api.lareserva.core.usecase.CreateRestaurant;
import com.api.lareserva.core.usecase.DeleteRestaurant;
import com.api.lareserva.core.usecase.FindRestaurant;
import com.api.lareserva.core.usecase.SearchRestaurants;
import com.api.lareserva.core.usecase.UpdateRestaurant;
import com.api.lareserva.presenter.RestaurantPresenter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
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
class RestaurantControllerTest {

  private static final String BASE_URL = "/lareserva/restaurant";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private CreateRestaurant createRestaurant;
  @MockBean private FindRestaurant findRestaurant;
  @MockBean private SearchRestaurants searchRestaurants;
  @MockBean private UpdateRestaurant updateRestaurant;
  @MockBean private DeleteRestaurant deleteRestaurant;
  @MockBean private RestaurantPresenter restaurantPresenter;

  @Test
  void shouldCreateRestaurantSuccessfully() throws Exception {
    final var request = validRestaurantRequest();
    final var response = validRestaurantResponse();
    final var presenterResponse = validRestaurantPresenterResponse();

    when(createRestaurant.execute(any(Restaurant.class))).thenReturn(response);
    when(restaurantPresenter.parseToResponse(response)).thenReturn(presenterResponse);

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.restaurantName").value(response.getRestaurantName()))
        .andExpect(jsonPath("$.cnpj").value(response.getCnpj()))
        .andExpect(jsonPath("$.address").value(response.getAddress()))
        .andExpect(jsonPath("$.city").value(response.getCity()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.typeOfFood").value(response.getTypeOfFood()))
        .andExpect(jsonPath("$.capacity").value(response.getCapacity()))
        .andExpect(jsonPath("$.numberOfTables").value(response.getNumberOfTables()))
        .andExpect(jsonPath("$.openingHours").isArray())
        .andExpect(jsonPath("$.openingHours[0].id").value(response.getOpeningHour().get(0).getId()))
        .andExpect(
            jsonPath("$.openingHours[0].dayOfWeek")
                .value(response.getOpeningHour().get(0).getDayOfWeek()))
        .andExpect(
            jsonPath("$.openingHours[0].openTime")
                .value(response.getOpeningHour().get(0).getOpenTime().toString()))
        .andExpect(
            jsonPath("$.openingHours[0].closeTime")
                .value(response.getOpeningHour().get(0).getCloseTime().toString()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));
  }

  @Test
  void shouldFindRestaurantSuccessfully() throws Exception {
    final var cnpj = "12345678901234";
    final var response = validRestaurantResponse();

    when(findRestaurant.execute(any(String.class))).thenReturn(Optional.of(response));
    when(restaurantPresenter.parseToResponse(response))
        .thenReturn(validRestaurantPresenterResponse());

    mockMvc
        .perform(
            get(BASE_URL + "/{cnpj}", cnpj)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cnpj)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.restaurantName").value(response.getRestaurantName()))
        .andExpect(jsonPath("$.cnpj").value(response.getCnpj()))
        .andExpect(jsonPath("$.address").value(response.getAddress()))
        .andExpect(jsonPath("$.city").value(response.getCity()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.typeOfFood").value(response.getTypeOfFood()))
        .andExpect(jsonPath("$.capacity").value(response.getCapacity()))
        .andExpect(jsonPath("$.numberOfTables").value(response.getNumberOfTables()))
        .andExpect(jsonPath("$.openingHours").isArray())
        .andExpect(jsonPath("$.openingHours[0].id").value(response.getOpeningHour().get(0).getId()))
        .andExpect(
            jsonPath("$.openingHours[0].dayOfWeek")
                .value(response.getOpeningHour().get(0).getDayOfWeek()))
        .andExpect(
            jsonPath("$.openingHours[0].openTime")
                .value(response.getOpeningHour().get(0).getOpenTime().toString()))
        .andExpect(
            jsonPath("$.openingHours[0].closeTime")
                .value(response.getOpeningHour().get(0).getCloseTime().toString()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));
  }

  @Test
  void shouldReturnNotFoundWhenRestaurantIsNotFound() throws Exception {
    final var cnpj = "12345678901234";

    when(findRestaurant.execute(any(String.class))).thenReturn(Optional.empty());

    mockMvc
        .perform(
            get(BASE_URL + "/{cnpj}", cnpj)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cnpj)))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldSearchRestaurantSuccessfully() throws Exception {
    final var restaurantName = "Doce Sonho";
    final var city = "Natal";
    final var typeOfFood = "Italian";
    final var response = List.of(validRestaurantResponse());
    final var presenterResponse = List.of(validRestaurantPresenterResponse());

    when(searchRestaurants.execute(restaurantName, city, typeOfFood)).thenReturn(response);
    when(restaurantPresenter.parseToResponse(any(Restaurant.class)))
        .thenReturn(presenterResponse.get(0));

    mockMvc
        .perform(
            get(BASE_URL + "/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantName", restaurantName)
                .param("city", city)
                .param("typeOfFood", typeOfFood))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(presenterResponse.get(0).id()))
        .andExpect(jsonPath("$[0].restaurantName").value(presenterResponse.get(0).restaurantName()))
        .andExpect(jsonPath("$[0].cnpj").value(presenterResponse.get(0).cnpj()))
        .andExpect(jsonPath("$[0].address").value(presenterResponse.get(0).address()))
        .andExpect(jsonPath("$[0].city").value(presenterResponse.get(0).city()))
        .andExpect(jsonPath("$[0].phoneNumber").value(presenterResponse.get(0).phoneNumber()))
        .andExpect(jsonPath("$[0].typeOfFood").value(presenterResponse.get(0).typeOfFood()))
        .andExpect(jsonPath("$[0].capacity").value(presenterResponse.get(0).capacity()))
        .andExpect(jsonPath("$[0].numberOfTables").value(presenterResponse.get(0).numberOfTables()))
        .andExpect(jsonPath("$[0].openingHours").isArray())
        .andExpect(
            jsonPath("$[0].openingHours[0].id")
                .value(presenterResponse.get(0).openingHours().get(0).id()))
        .andExpect(
            jsonPath("$[0].openingHours[0].dayOfWeek")
                .value(presenterResponse.get(0).openingHours().get(0).dayOfWeek()))
        .andExpect(
            jsonPath("$[0].openingHours[0].openTime")
                .value(presenterResponse.get(0).openingHours().get(0).openTime().toString()))
        .andExpect(
            jsonPath("$[0].openingHours[0].closeTime")
                .value(presenterResponse.get(0).openingHours().get(0).closeTime().toString()))
        .andExpect(jsonPath("$[0].email").value(presenterResponse.get(0).email()));
  }

  @Test
  void shouldReturnEmptyListWhenSearchAndThereAreNoRestaurantsWithTheSpecifiedParameters()
      throws Exception {
    final var restaurantName = "Doce Sonho";
    final var city = "Natal";
    final var typeOfFood = "Italian";

    when(searchRestaurants.execute(restaurantName, city, typeOfFood)).thenReturn(List.of());

    mockMvc
        .perform(
            get(BASE_URL + "/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantName", restaurantName)
                .param("city", city)
                .param("typeOfFood", typeOfFood))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void shouldUpdateRestaurantSuccessfully() throws Exception {
    final var cnpj = "12345678901234";
    final var request = validUpdateRestaurantRequest();
    final var response = validUpdateRestaurantResponse();
    final var presenterResponse = validUpdateRestaurantPresenterResponse();

    when(updateRestaurant.execute(eq(cnpj), any(UpdateRestaurantDto.class))).thenReturn(response);
    when(restaurantPresenter.parseToResponse(response)).thenReturn(presenterResponse);

    mockMvc
        .perform(
            put(BASE_URL + "/{cnpj}", cnpj)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(presenterResponse.id()))
        .andExpect(jsonPath("$.restaurantName").value(presenterResponse.restaurantName()))
        .andExpect(jsonPath("$.cnpj").value(presenterResponse.cnpj()))
        .andExpect(jsonPath("$.address").value(presenterResponse.address()))
        .andExpect(jsonPath("$.city").value(presenterResponse.city()))
        .andExpect(jsonPath("$.phoneNumber").value(presenterResponse.phoneNumber()))
        .andExpect(jsonPath("$.typeOfFood").value(presenterResponse.typeOfFood()))
        .andExpect(jsonPath("$.capacity").value(presenterResponse.capacity()))
        .andExpect(jsonPath("$.numberOfTables").value(presenterResponse.numberOfTables()))
        .andExpect(jsonPath("$.openingHours").isArray())
        .andExpect(
            jsonPath("$.openingHours[0].id").value(presenterResponse.openingHours().get(0).id()))
        .andExpect(
            jsonPath("$.openingHours[0].dayOfWeek")
                .value(presenterResponse.openingHours().get(0).dayOfWeek()))
        .andExpect(
            jsonPath("$.openingHours[0].openTime")
                .value(presenterResponse.openingHours().get(0).openTime().toString()))
        .andExpect(
            jsonPath("$.openingHours[0].closeTime")
                .value(presenterResponse.openingHours().get(0).closeTime().toString()))
        .andExpect(jsonPath("$.email").value(presenterResponse.email()));
  }

  @Test
  void shouldDeleteRestaurantSuccessfully() throws Exception {
    final var cnpj = "12345678901234";

    mockMvc
        .perform(
            delete(BASE_URL + "/{cnpj}", cnpj)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cnpj)))
        .andExpect(status().isNoContent());
  }
}
