package com.api.lareserva.core.domain;

import com.api.lareserva.core.domain.exception.DomainException;
import com.api.lareserva.core.domain.exception.ErrorDetail;
import com.api.lareserva.core.dto.RestaurantDto;
import com.api.lareserva.core.dto.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import java. util. Date;
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
public class Reservation {

  private Integer id;

  @NotNull(message = "Reservation Date is required")
  private Date reservationDate;

  @NotNull(message = "Reservation Time is required")
  private LocalTime reservationTime;

  @NotNull(message = "Number of People is required")
  private Integer numberOfPeople;

  @NotNull(message = "Restaurant is required")
  private RestaurantDto restaurant;

  @NotNull(message = "User is required")
  private UserDto user;

  public static Reservation createReservation(
      final Date reservationDate,
      final LocalTime reservationTime,
      final Integer numberOfPeople,
      final RestaurantDto restaurant,
      final UserDto user) {

    final var reservation =
        Reservation.builder()
            .reservationDate(reservationDate)
            .reservationTime(reservationTime)
            .numberOfPeople(numberOfPeople)
            .restaurant(restaurant)
            .user(user)
            .build();

    validate(reservation);

    return reservation;
  }

  private static void validate(final Reservation reservation) {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);

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
