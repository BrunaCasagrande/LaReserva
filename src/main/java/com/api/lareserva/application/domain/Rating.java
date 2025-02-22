package com.api.lareserva.application.domain;

import com.api.lareserva.application.dto.RestaurantDto;
import com.api.lareserva.application.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {

  private Integer id;

  @NotNull(message = "Stars is required")
  private Integer stars;

  private String comment;

  @NotNull(message = "Date is required")
  private Date date;

  @NotNull(message = "Restaurant is required")
  private RestaurantDto restaurant;

  @NotNull(message = "User is required")
  private UserDto user;

  public static Rating createRating(
      final Integer stars,
      final String comment,
      final Date date,
      final RestaurantDto restaurant,
      final UserDto user) {
    return Rating.builder()
        .stars(stars)
        .comment(comment)
        .date(date)
        .restaurant(restaurant)
        .user(user)
        .build();
  }
}
