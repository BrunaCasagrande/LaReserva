package com.api.lareserva.infra.gateway.fixture;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import com.api.lareserva.infra.persistence.entity.RatingEntity;
import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import com.api.lareserva.infra.persistence.entity.UserEntity;
import java.time.LocalDate;
import java.util.Date;

public class RatingGatewayImplTestFixture {

  public static final Integer EXISTING_RATING_ID = 1;
  public static final Integer STARS = 5;
  public static final String COMMENT = "Excelente comida e atendimento!";
  public static final LocalDate LOCAL_RATING_DATE = LocalDate.now();
  public static final Date RATING_DATE = java.sql.Date.valueOf(LOCAL_RATING_DATE);

  public static Rating existingRating() {
    return Rating.builder()
        .id(EXISTING_RATING_ID)
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(existingRestaurantDto())
        .user(existingUserDto())
        .build();
  }

  public static Rating newRating() {
    return Rating.builder()
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(existingRestaurantDto())
        .user(existingUserDto())
        .build();
  }

  public static RatingEntity existingRatingEntity() {
    return RatingEntity.builder()
        .id(EXISTING_RATING_ID)
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(existingRestaurantEntity())
        .user(existingUserEntity())
        .build();
  }

  public static RestaurantDto existingRestaurantDto() {
    return RestaurantDto.builder()
        .id(10)
        .restaurantName("Mamma Mia")
        .cnpj("12345678901234")
        .address("Rua das Pizzas, 45")
        .city("São Paulo")
        .phoneNumber("11987654321")
        .typeOfFood("Italiana")
        .capacity(50)
        .numberOfTables(10)
        .email("contato@mammamia.com")
        .password("SenhaSegura123")
        .build();
  }

  public static UserDto existingUserDto() {
    return UserDto.builder()
        .id(5)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11999999999")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }

  public static RestaurantEntity existingRestaurantEntity() {
    return RestaurantEntity.builder()
        .id(10)
        .restaurantName("Mamma Mia")
        .cnpj("12345678901234")
        .address("Rua das Pizzas, 45")
        .city("São Paulo")
        .phoneNumber("11987654321")
        .typeOfFood("Italiana")
        .capacity(50)
        .numberOfTables(10)
        .email("contato@mammamia.com")
        .password("SenhaSegura123")
        .build();
  }

  public static UserEntity existingUserEntity() {
    return UserEntity.builder()
        .id(5)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11999999999")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
