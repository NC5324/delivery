package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Topping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ToppingRepository extends JpaRepository<Topping, Long> {
    Optional<Topping> findByName(String name);

    @Query("SELECT t FROM Topping t WHERE " +
            "LOWER(t.type) " +
            "LIKE :#{#type == null || #type.isEmpty()? '%' : '%'+#type+'%'}")
    Page<Topping> findPageToppings(Pageable pageRequest, String type);
}
