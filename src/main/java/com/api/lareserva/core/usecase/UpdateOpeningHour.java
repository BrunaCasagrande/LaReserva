package com.api.lareserva.core.usecase;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.core.dto.OpeningHourDto;
import com.api.lareserva.core.gateway.OpeningHourGateway;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOpeningHour {

  private final OpeningHourGateway openingHourGateway;

  public List<OpeningHour> execute(final int restaurantId, final List<OpeningHourDto> request) {
    final var foundOpeningHours = openingHourGateway.findByRestaurantId(restaurantId);

    if (foundOpeningHours.isEmpty()) {
      return openingHourGateway.update(restaurantId, toOpeningHours(request));
    }

    final List<OpeningHour> updatedOpeningHours = new ArrayList<>();

    for (OpeningHourDto openingHour : request) {
      final Optional<OpeningHour> existingOpeningHour =
          foundOpeningHours.stream().filter(o -> o.getId().equals(openingHour.getId())).findFirst();

      if (existingOpeningHour.isPresent()) {
        OpeningHour updated = existingOpeningHour.get();
        updated.setDayOfWeek(openingHour.getDayOfWeek());
        updated.setOpenTime(openingHour.getOpenTime());
        updated.setCloseTime(openingHour.getCloseTime());
        updatedOpeningHours.add(updated);
      } else {
        updatedOpeningHours.add(toOpeningHour(openingHour));
      }
    }

    return openingHourGateway.update(restaurantId, updatedOpeningHours);
  }

  private List<OpeningHour> toOpeningHours(final List<OpeningHourDto> openingHourDtos) {
    return openingHourDtos.stream().map(this::toOpeningHour).toList();
  }

  private OpeningHour toOpeningHour(final OpeningHourDto openingHourDto) {
    return OpeningHour.builder()
        .id(openingHourDto.getId())
        .dayOfWeek(openingHourDto.getDayOfWeek())
        .openTime(openingHourDto.getOpenTime())
        .closeTime(openingHourDto.getCloseTime())
        .build();
  }
}
