package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
