package com.api.lareserva.presenter.response;

import java.util.List;
import lombok.Builder;

@Builder
public record RestaurantPresenterResponse(
    int id,
    String restaurantName,
    String cnpj,
    String address,
    String city,
    String phoneNumber,
    String typeOfFood,
    Integer capacity,
    Integer numberOfTables,
    List<OpeningHourPresenterResponse> openingHours,
    String email) {}
