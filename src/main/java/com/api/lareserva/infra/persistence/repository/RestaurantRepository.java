package com.api.lareserva.infra.persistence.repository;

import com.api.lareserva.infra.persistence.entity.RestaurantEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

  Optional<RestaurantEntity> findByCnpj(final String cnpj);

  @Query(
      "SELECT r FROM RestaurantEntity r WHERE "
          + "(:restaurantName IS NULL OR LOWER(r.restaurantName) LIKE LOWER(CONCAT('%', :restaurantName, '%'))) AND "
          + "(:city IS NULL OR LOWER(r.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND "
          + "(:typeOfFood IS NULL OR LOWER(r.typeOfFood) LIKE LOWER(CONCAT('%', :typeOfFood, '%')))")
  List<RestaurantEntity> findAllBy(
      @Param("restaurantName") final String restaurantName,
      @Param("city") final String city,
      @Param("typeOfFood") final String typeOfFood);

  void deleteByCnpj(final String cnpj);
}
