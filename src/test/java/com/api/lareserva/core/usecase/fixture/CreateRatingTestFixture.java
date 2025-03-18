package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import java.util.Date;

public class CreateRatingTestFixture {

  public static final Integer EXISTING_RATING_ID = 1;
  public static final Integer STARS = 5;
  public static final String COMMENT = "Excellent food and service!";
  public static final Date RATING_DATE = new Date();
  public static final Integer RESTAURANT_ID = 10;
  public static final Integer USER_ID = 5;

  public static Rating validRatingGatewayResponse() {
    return Rating.builder()
        .id(EXISTING_RATING_ID)
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(validRestaurant())
        .user(validUser())
        .build();
  }

  public static Rating validRatingRequest() {
    return Rating.builder()
        .stars(STARS)
        .comment(COMMENT)
        .date(RATING_DATE)
        .restaurant(validRestaurant())
        .user(validUser())
        .build();
  }

  private static RestaurantDto validRestaurant() {
    return RestaurantDto.builder()
        .id(RESTAURANT_ID)
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

  private static UserDto validUser() {
    return UserDto.builder()
        .id(USER_ID)
        .name("Bruna Casagrande")
        .cpf("12345678900")
        .phoneNumber("11999999999")
        .email("bruna@email.com")
        .password("Bru123456!")
        .build();
  }
}
