package com.api.lareserva.presenter;

import com.api.lareserva.core.domain.Restaurant;
import com.api.lareserva.core.dto.OpeningHourDto;
import com.api.lareserva.presenter.response.OpeningHourPresenterResponse;
import com.api.lareserva.presenter.response.RestaurantPresenterResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPresenter {

  public RestaurantPresenterResponse parseToResponse(final Restaurant restaurant) {
    return RestaurantPresenterResponse.builder()
        .id(restaurant.getId())
        .restaurantName(restaurant.getRestaurantName())
        .cnpj(restaurant.getCnpj())
        .address(restaurant.getAddress())
        .city(restaurant.getCity())
        .phoneNumber(restaurant.getPhoneNumber())
        .typeOfFood(restaurant.getTypeOfFood())
        .capacity(restaurant.getCapacity())
        .numberOfTables(restaurant.getNumberOfTables())
        .openingHours(toOpeningHourPresenterResponse(restaurant.getOpeningHour()))
        .email(restaurant.getEmail())
        .build();
  }

  public List<RestaurantPresenterResponse> parseToResponse(final List<Restaurant> restaurants) {
    return restaurants.stream().map(this::parseToResponse).toList();
  }

  private List<OpeningHourPresenterResponse> toOpeningHourPresenterResponse(
      final List<OpeningHourDto> openingHours) {
    return openingHours.stream()
        .map(
            openingHour ->
                OpeningHourPresenterResponse.builder()
                    .id(openingHour.getId())
                    .dayOfWeek(openingHour.getDayOfWeek())
                    .openTime(openingHour.getOpenTime())
                    .closeTime(openingHour.getCloseTime())
                    .build())
        .toList();
  }
}
