package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Transaction;
import com.nnmpizza.delivery.payload.request.ProductRequest;
import com.nnmpizza.delivery.repository.TransactionRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody Member member){

        boolean isNew = !transactionRepository.existsById(member.getId());
        Transaction newTransaction = new Transaction(member);

        Map<String, Object> response = new HashMap<>();
        response.put("Product", newTransaction);
        if(!isNew){
            response.put("message", "Успешно редактиран.");
        } else {
            response.put("message", "Успешно записан.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
