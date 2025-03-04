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
public class RestaurantDto {

  private Integer id;

  private String restaurantName;

  private String cnpj;

  private String address;

  private String city;

  private String phoneNumber;

  private String typeOfFood;

  private Integer capacity;

  private Integer numberOfTables;

  private String email;

  private String password;
}
