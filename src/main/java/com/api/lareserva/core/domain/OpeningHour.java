package com.api.lareserva.core.domain;

import com.api.lareserva.core.domain.exception.DomainException;
import com.api.lareserva.core.domain.exception.ErrorDetail;
import com.api.lareserva.core.dto.RestaurantDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
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
public class OpeningHour {

  private Integer id;

  @NotBlank(message = "Day of week is required")
  private String dayOfWeek;

  @NotNull(message = "Open time is required")
  private LocalTime openTime;

  @NotNull(message = "Close time is required")
  private LocalTime closeTime;

  @NotNull(message = "Restaurant is required")
  private RestaurantDto restaurant;

  public static OpeningHour createOpeningHour(
      final String dayOfWeek,
      final LocalTime openTime,
      final LocalTime closeTime,
      final RestaurantDto restaurant) {

    final var openingHour =
        OpeningHour.builder()
            .dayOfWeek(dayOfWeek)
            .openTime(openTime)
            .closeTime(closeTime)
            .restaurant(restaurant)
            .build();

    validate(openingHour);

    return openingHour;
  }

  private static void validate(final OpeningHour openingHour) {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<OpeningHour>> violations = validator.validate(openingHour);

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
