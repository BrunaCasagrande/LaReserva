package com.api.lareserva.presenter.response;

import java.util.Date;
import lombok.Builder;

@Builder
public record RatingPresenterResponse(
    int id, int stars, String comment, Date date, Integer restaurantId, Integer userId) {}
