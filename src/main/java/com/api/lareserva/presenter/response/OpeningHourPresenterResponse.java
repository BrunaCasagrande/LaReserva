package com.api.lareserva.presenter.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record OpeningHourPresenterResponse(
    int id,
    String dayOfWeek,
    @JsonFormat(pattern = "HH:mm") LocalTime openTime,
    @JsonFormat(pattern = "HH:mm") LocalTime closeTime) {}
