package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi FROM OrderItem oi WHERE " +
            "oi.product.id = :productId AND " +
            "oi.quantity = :quantity")
    public Optional<OrderItem> findByProductAndQuantity(Long productId, Long quantity);
}
