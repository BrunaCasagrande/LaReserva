package com.api.lareserva.presenter.response;

import lombok.Builder;

@Builder
public record UserPresenterResponse(
    int id, String name, String cpf, String phoneNumber, String email) {}
