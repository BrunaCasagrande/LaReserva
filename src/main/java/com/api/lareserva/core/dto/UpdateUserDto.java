package com.api.lareserva.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Phone Number is required")
  private String phoneNumber;

  @Email(message = "E-mail is required")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;
}
