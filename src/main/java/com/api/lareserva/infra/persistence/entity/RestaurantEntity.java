package com.api.lareserva.infra.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import jdk.jshell.Snippet;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "restaurant_name", nullable = false)
  private String restaurantName;

  @Column(name = "cnpj", nullable = false, unique = true)
  private String cnpj;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(name = "type_of_food", nullable = false)
  private String typeOfFood;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  @Column(name = "number_of_tables", nullable = false)
  private Integer numberOfTables;

  @OneToMany(
      mappedBy = "restaurant",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<OpeningHourEntity> openingHours = new ArrayList<>();

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;
}
