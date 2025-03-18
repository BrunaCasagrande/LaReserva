package com.api.lareserva.infra.gateway;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import com.api.lareserva.core.gateway.RatingGateway;
import com.api.lareserva.infra.persistence.entity.RatingEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.entity.UserEntity;
import com.api.lareserva.infra.persistence.repository.RatingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingGatewayImpl implements RatingGateway {

  private final RatingRepository ratingRepository;

  @Override
  public Rating save(final Rating rating) {
    final var entity =
        RatingEntity.builder()
            .stars(rating.getStars())
            .comment(rating.getComment())
            .date(rating.getDate())
            .restaurant(toRestaurantEntity(rating.getRestaurant()))
            .user(toUserEntity(rating.getUser()))
            .build();

    final var saved = ratingRepository.save(entity);
    return toRatingDomain(saved);
  }

  @Override
  public List<Rating> findByRestaurant(final Integer restaurantId) {
    return ratingRepository.findByRestaurantId(restaurantId).stream()
        .map(this::toRatingDomain)
        .toList();
  }

  @Override
  public List<Rating> findByUser(final Integer userId) {
    return ratingRepository.findByUserId(userId).stream().map(this::toRatingDomain).toList();
  }

  private Rating toRatingDomain(final RatingEntity entity) {
    return Rating.builder()
        .id(entity.getId())
        .stars(entity.getStars())
        .comment(entity.getComment())
        .date(entity.getDate())
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
