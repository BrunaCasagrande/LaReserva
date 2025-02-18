package com.api.lareserva.application.domain;

import com.api.lareserva.application.dto.OpeningHourDto;
import jakarta.validation.constraints.*;
import java.util.List;
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

  @NotBlank(message = "Address required")
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
  @Size(max = 255, message = "Email length must be less than 255 characters")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 16, message = "Password length must be between 8 and 16 characters")
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
    return Restaurant.builder()
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
  }
}
