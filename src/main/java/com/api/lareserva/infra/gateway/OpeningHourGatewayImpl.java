package com.api.lareserva.infra.gateway;

import static java.lang.String.format;

import com.api.lareserva.core.domain.OpeningHour;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.gateway.OpeningHourGateway;
import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.OpeningHourEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.repository.OpeningHourRepository;
import com.api.lareserva.infra.persistence.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpeningHourGatewayImpl implements OpeningHourGateway {

  private final OpeningHourRepository openingHourRepository;
  private final RestaurantRepository restaurantRepository;

  @Override
  public List<OpeningHour> findByRestaurantId(final int restaurantId) {
    final var entities = openingHourRepository.findByRestaurantId(restaurantId);

    return entities.stream()
        .map(
            entity -> {
              final var domain =
                  OpeningHour.createOpeningHour(
                      entity.getDayOfWeek(),
                      entity.getOpenTime(),
                      entity.getCloseTime(),
                      toRestaurantDto(entity.getRestaurant()));

              domain.setId(entity.getId());

              return domain;
            })
        .toList();
  }

  @Override
  public List<OpeningHour> update(final int restaurantId, final List<OpeningHour> openingHours) {
    final RestaurantEntity restaurant =
        restaurantRepository
            .findById(restaurantId)
            .orElseThrow(
                () ->
                    new GatewayException(
                        format("Restaurant not found for id=[%s].", restaurantId)));

    final List<OpeningHourEntity> updatedOpeningHours = new ArrayList<>();

    for (OpeningHour openingHour : openingHours) {
      final Optional<OpeningHourEntity> existingOpeningHour =
          restaurant.getOpeningHours().stream()
              .filter(o -> o.getId().equals(openingHour.getId()))
              .findFirst();

      if (existingOpeningHour.isPresent()) {
        OpeningHourEntity openingHourEntity = existingOpeningHour.get();
        openingHourEntity.setDayOfWeek(openingHour.getDayOfWeek());
        openingHourEntity.setOpenTime(openingHour.getOpenTime());
        openingHourEntity.setCloseTime(openingHour.getCloseTime());
        updatedOpeningHours.add(openingHourEntity);
      } else {
        OpeningHourEntity newOpeningHourEntity = new OpeningHourEntity();
        newOpeningHourEntity.setDayOfWeek(openingHour.getDayOfWeek());
        newOpeningHourEntity.setOpenTime(openingHour.getOpenTime());
        newOpeningHourEntity.setCloseTime(openingHour.getCloseTime());
        newOpeningHourEntity.setRestaurant(restaurant);
        updatedOpeningHours.add(newOpeningHourEntity);
      }
    }

    restaurant.getOpeningHours().clear();
    restaurant.getOpeningHours().addAll(updatedOpeningHours);

    restaurantRepository.save(restaurant);

    return updatedOpeningHours.stream()
        .map(
            openingHourEntity ->
                OpeningHour.builder()
                    .id(openingHourEntity.getId())
                    .dayOfWeek(openingHourEntity.getDayOfWeek())
                    .openTime(openingHourEntity.getOpenTime())
                    .closeTime(openingHourEntity.getCloseTime())
                    .build())
        .toList();
  }

  private RestaurantDto toRestaurantDto(final RestaurantEntity entity) {
    return RestaurantDto.builder()
        .id(entity.getId())
        .restaurantName(entity.getRestaurantName())
        .cnpj(entity.getCnpj())
        .address(entity.getAddress())
        .city(entity.getCity())
        .phoneNumber(entity.getPhoneNumber())
        .typeOfFood(entity.getTypeOfFood())
        .capacity(entity.getCapacity())
        .numberOfTables(entity.getNumberOfTables())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .build();
  }
}
