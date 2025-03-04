package com.api.lareserva.core.dto;

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
public class UserDto {

  private Integer id;

  private String name;

  private String cpf;

  private String phoneNumber;

  private String email;

  private String password;
}
