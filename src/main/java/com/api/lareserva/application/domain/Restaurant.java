package com.api.lareserva.application.domain;

import com.api.lareserva.application.domain.exception.DomainException;
import com.api.lareserva.application.domain.exception.ErrorDetail;
import com.api.lareserva.application.dto.OpeningHourDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class Restaurant {

  private Integer id;

  @NotBlank(message = "Restaurant name is required")
  private String restaurantName;

  @NotBlank(message = "CNPJ is required")
  @Pattern(regexp = "\\d{14}", message = "CNPJ must be 14 digits")
  private String cnpj;

  @NotBlank(message = "Address is required")
  private String address;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "Phone number is required")
  @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
  private String phoneNumber;

  @NotBlank(message = "Type of food is required")
  private String typeOfFood;

  @NotNull(message = "Capacity is required")
  private Integer capacity;

  @NotNull(message = "Number of tables is required")
  private Integer numberOfTables;

  @NotNull(message = "Opening hour is required")
  private List<OpeningHourDto> openingHour;

  @NotBlank(message = "E-mail is required")
  @Size(max = 255, message = "E-mail length must be less than 255 characters")
  @Email(message = "E-mail should be valid")
  private String email;

  @NotBlank(message = "Password is required")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
      message =
          "The password must be between 8 and 16 characters long, including letters, numbers and at least one special character (@$!%*?&).")
  private String password;

  public static Restaurant createRestaurant(
      final String restaurantName,
      final String cnpj,
      final String address,
      final String city,
      final String phoneNumber,
      final String typeOfFood,
      final Integer capacity,
      final Integer numberOfTables,
      final List<OpeningHourDto> openingHour,
      final String email,
      final String password) {

    final var restaurant =
        Restaurant.builder()
            .restaurantName(restaurantName)
            .cnpj(cnpj)
            .address(address)
            .city(city)
            .phoneNumber(phoneNumber)
            .typeOfFood(typeOfFood)
            .capacity(capacity)
            .numberOfTables(numberOfTables)
            .openingHour(openingHour)
            .email(email)
            .password(password)
            .build();

    validate(restaurant);

    return restaurant;
  }

  private static void validate(final Restaurant restaurant) {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);

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
