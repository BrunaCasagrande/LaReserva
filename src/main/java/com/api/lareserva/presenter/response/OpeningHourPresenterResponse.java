package com.api.lareserva.presenter.response;

import java.time.LocalTime;
import lombok.Builder;

@Builder
public record OpeningHourPresenterResponse(
    int id, String dayOfWeek, LocalTime openTime, LocalTime closeTime) {}
