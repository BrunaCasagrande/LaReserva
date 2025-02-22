package com.api.lareserva.application.dto;

import lombok.*;

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
