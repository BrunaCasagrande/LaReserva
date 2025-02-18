package com.api.lareserva.application.dto;

import lombok.*;

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
