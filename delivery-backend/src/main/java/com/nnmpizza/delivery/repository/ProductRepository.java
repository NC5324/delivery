package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Member> findByName(String name);

    Boolean existsByName(String name);

    List<Product> findAll();
}
