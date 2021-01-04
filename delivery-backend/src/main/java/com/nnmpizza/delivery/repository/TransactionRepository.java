package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


}
