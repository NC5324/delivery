package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Member> findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByNameIgnoreCase(String name);

    List<Product> findAll();

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) " +
            "LIKE :#{#name == null || #name.isEmpty()? '%' : '%'+#name+'%'} AND " +
            "LOWER(p.description) " +
            "LIKE :#{#description == null || #description.isEmpty()? '%' : '%'+#description+'%'} AND " +
            "LOWER(p.type) " +
            "LIKE :#{#type == null || #type.isEmpty()? '%' : '%'+#type+'%'} AND " +
            "CONCAT(:price, '') " +
            "LIKE :#{#price == null || #price.isEmpty()? '%' : '%'+#price+'%'}")
    Page<Product> findPageProducts(Pageable pageRequest, String name, String description, String type, Double price);

    Optional<Product> findPersonById(Long id);
}
