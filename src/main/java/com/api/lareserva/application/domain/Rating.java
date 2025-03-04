package com.api.lareserva.application.domain;

import com.api.lareserva.application.domain.exception.DomainException;
import com.api.lareserva.application.domain.exception.ErrorDetail;
import com.api.lareserva.application.dto.RestaurantDto;
import com.api.lareserva.application.dto.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    final var rating =
        Rating.builder()
            .stars(stars)
            .comment(comment)
            .date(date)
            .restaurant(restaurant)
            .user(user)
            .build();

    validate(rating);

    return rating;
  }

  private static void validate(final Rating rating) {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<Rating>> violations = validator.validate(rating);

    if (!violations.isEmpty()) {
      final List<ErrorDetail> errors =
          violations.stream()
              .map(
                  violation ->
                      new ErrorDetail(
                          violation.getPropertyPath().toString(),
                          violation.getMessage(),
                          violation.getInvalidValue()))
              .toList();

      final String firstErrorMessage = errors.get(0).errorMessage();

      throw new DomainException(firstErrorMessage, "domain_exception", errors);
    }
  }
}
