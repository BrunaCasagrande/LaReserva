package com.api.lareserva.presenter.response;

import java.time.LocalTime;
import java.util.Date;
import lombok.Builder;

@Builder
public record ReservationPresenterResponse(
        int id,
        Date reservationDate,
        LocalTime reservationTime,
        int numberOfPeople,
        UserPresenterResponse user
) {}
