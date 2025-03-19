package com.api.lareserva.core.dto;

import lombok.Builder;

@Builder
public record UpdateRestaurantDto(
    String address,
    String phoneNumber,
    String typeOfFood,
    Integer capacity,
    Integer numberOfTables,
    String email,
    String password) {}
