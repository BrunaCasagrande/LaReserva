package com.api.lareserva.core.usecase.fixture;

import com.api.lareserva.core.domain.Rating;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import java.util.Date;
import java.util.List;

public class SearchRatingTestFixture {

  public static final Integer RESTAURANT_ID = 10;
  public static final Integer USER_ID = 5;

  public static List<Rating> validRatingsList() {
    return List.of(
        Rating.builder()
            .id(1)
            .stars(5)
            .comment("Excellent food and service!")
            .date(new Date())
            .restaurant(validRestaurant())
            .user(validUser())
            .build(),
        Rating.builder()
            .id(2)
            .stars(4)
            .comment("Good experience, but service was slow.")
            .date(new Date())
            .restaurant(validRestaurant())
            .user(validUser())
            .build());
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
