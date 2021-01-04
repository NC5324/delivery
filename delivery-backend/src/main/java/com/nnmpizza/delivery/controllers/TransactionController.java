package com.nnmpizza.delivery.controllers;
import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Transaction;
import com.nnmpizza.delivery.payload.request.TransactionRequest;
import com.nnmpizza.delivery.payload.response.MessageResponse;
import com.nnmpizza.delivery.repository.MemberRepository;
import com.nnmpizza.delivery.repository.ProductRepository;
import com.nnmpizza.delivery.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    final TransactionRepository transactionRepository;
    final MemberRepository memberRepository;
    final ProductRepository productRepository;

    public TransactionController(TransactionRepository transactionRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        System.out.println("is null??? "+transactionRequest.getMemberID());

        /*TODO: check if exists by memberID and edit instead of adding new (one order per person)*/
        var transaction = new Transaction();
        var member = memberRepository.findById(transactionRequest.getMemberID());
        if(member.isPresent()) {
            transaction.setMember(member.get());

            Set<Long> productIDs = transactionRequest.getProducts();
            Set<Product> products = new HashSet<>();

            if (productIDs != null) {
                productIDs.forEach(productID -> {
                    if (productRepository.findById(productID).isPresent()) {
                        products.add(productRepository.findById(productID).get());
                    }
                });
            }

            transaction.setProducts(products);
            transactionRepository.save(transaction);

            return ResponseEntity.ok(new MessageResponse("Transaction registered successfully!"));
        }

        return ResponseEntity.ok(new MessageResponse("Error trying to register transaction"));
    }
}
