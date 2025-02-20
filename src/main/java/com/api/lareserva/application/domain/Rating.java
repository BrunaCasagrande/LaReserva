package com.api.lareserva.application.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

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
    private Restaurant restaurant;

    @NotNull(message = "User is required")
    private User user;

    public static Rating createRating(final Integer stars,
                                      final String comment,
                                      final Date date,
                                      final Restaurant restaurant,
                                      final User user){
        return Rating.builder()
                .stars(stars)
                .comment(comment)
                .date(date)
                .restaurant(restaurant)
                .user(user).build();
    }
}
