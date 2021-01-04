package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Transaction;
import com.nnmpizza.delivery.payload.request.MemberRequest;
import com.nnmpizza.delivery.payload.request.ProductRequest;
import com.nnmpizza.delivery.payload.request.TransactionRequest;
import com.nnmpizza.delivery.repository.MemberRepository;
import com.nnmpizza.delivery.repository.TransactionRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    final TransactionRepository transactionRepository;
    final MemberRepository memberRepository;

    public TransactionController(TransactionRepository transactionRepository, MemberRepository memberRepository) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER') or hasRole('GUEST')")
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER') or hasRole('GUEST')")
    public ResponseEntity<?> saveTransaction(@RequestBody TransactionRequest transactionRequest){
        boolean isNew = transactionRequest.getId() == null;

        Transaction newTransaction = new Transaction(transactionRequest);
        transactionRepository.save(newTransaction);

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
