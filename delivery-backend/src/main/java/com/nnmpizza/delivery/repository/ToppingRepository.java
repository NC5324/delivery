package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToppingRepository extends JpaRepository<Topping, Long> {
    Optional<Topping> findByName(String name);
}
