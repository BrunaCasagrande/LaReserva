package com.api.lareserva.core.gateway;

import com.api.lareserva.core.domain.OpeningHour;
import java.util.List;

public interface OpeningHourGateway {

  List<OpeningHour> findByRestaurantId(final int restaurantId);

  List<OpeningHour> update(final int restaurantId, final List<OpeningHour> openingHours);
}
