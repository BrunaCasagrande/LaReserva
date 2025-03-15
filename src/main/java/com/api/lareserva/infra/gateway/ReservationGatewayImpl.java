package com.api.lareserva.infra.gateway;

import static java.lang.String.format;

import com.api.lareserva.core.domain.Reservation;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import com.api.lareserva.core.gateway.ReservationGateway;
import com.api.lareserva.infra.gateway.exception.GatewayException;
import com.api.lareserva.infra.persistence.entity.ReservationEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.entity.UserEntity;
import com.api.lareserva.infra.persistence.repository.ReservationRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationGatewayImpl implements ReservationGateway {

  private static final String FIND_ERROR_MESSAGE = "Reservation with ID=[%d] not found.";

  private final ReservationRepository reservationRepository;

  @Override
  public Reservation save(final Reservation reservation) {
    final var entity =
        ReservationEntity.builder()
            .reservationDate(reservation.getReservationDate())
            .reservationTime(reservation.getReservationTime())
            .numberOfPeople(reservation.getNumberOfPeople())
            .restaurant(toRestaurantEntity(reservation.getRestaurant()))
            .user(toUserEntity(reservation.getUser()))
            .build();

    final var saved = reservationRepository.save(entity);
    return toReservationDomain(saved);
  }

  @Override
  public Optional<Reservation> findById(final Integer id) {
    return reservationRepository.findById(id).map(this::toReservationDomain);
  }

  @Override
  public List<Reservation> findByRestaurantAndDate(
      final Integer restaurantId, final Date reservationDate) {
    return reservationRepository
        .findByRestaurantIdAndReservationDate(restaurantId, reservationDate)
        .stream()
        .map(this::toReservationDomain)
        .toList();
  }

  @Override
  public Reservation update(final Reservation reservation) {
    final var entity =
        reservationRepository
            .findById(reservation.getId())
            .orElseThrow(
                () -> new GatewayException(format(FIND_ERROR_MESSAGE, reservation.getId())));

    entity.setReservationDate(reservation.getReservationDate());
    entity.setReservationTime(reservation.getReservationTime());
    entity.setNumberOfPeople(reservation.getNumberOfPeople());

    return toReservationDomain(reservationRepository.save(entity));
  }

  @Override
  public void deleteById(final Integer id) {
    reservationRepository.deleteById(id);
  }

  private Reservation toReservationDomain(final ReservationEntity entity) {
    return Reservation.builder()
        .id(entity.getId())
        .reservationDate(entity.getReservationDate())
        .reservationTime(entity.getReservationTime())
        .numberOfPeople(entity.getNumberOfPeople())
        .restaurant(toRestaurantDto(entity.getRestaurant()))
        .user(toUserDto(entity.getUser()))
        .build();
  }

  private RestaurantEntity toRestaurantEntity(final RestaurantDto dto) {
    return RestaurantEntity.builder()
        .id(dto.getId())
        .restaurantName(dto.getRestaurantName())
        .cnpj(dto.getCnpj())
        .address(dto.getAddress())
        .city(dto.getCity())
        .phoneNumber(dto.getPhoneNumber())
        .typeOfFood(dto.getTypeOfFood())
        .capacity(dto.getCapacity())
        .numberOfTables(dto.getNumberOfTables())
        .email(dto.getEmail())
        .password(dto.getPassword())
        .build();
  }

  private UserEntity toUserEntity(final UserDto dto) {
    return UserEntity.builder()
        .id(dto.getId())
        .name(dto.getName())
        .cpf(dto.getCpf())
        .phoneNumber(dto.getPhoneNumber())
        .email(dto.getEmail())
        .password(dto.getPassword())
        .build();
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

  private UserDto toUserDto(final UserEntity entity) {
    return UserDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .cpf(entity.getCpf())
        .phoneNumber(entity.getPhoneNumber())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .build();
  }
}
