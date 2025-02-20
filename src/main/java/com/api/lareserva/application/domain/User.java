package com.api.lareserva.application.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  private Integer id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "CPF is required")
  @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
  private String cpf;

  @NotBlank(message = "Phone number is required")
  @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
  private String phoneNumber;

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

  public static User createUser(
      final String name,
      final String cpf,
      final String phoneNumber,
      final String email,
      final String password) {
    return User.builder()
        .name(name)
        .cpf(cpf)
        .phoneNumber(phoneNumber)
        .email(email)
        .password(password)
        .build();
  }
}
