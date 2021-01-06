package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Order;
import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Transaction;
import com.nnmpizza.delivery.payload.beans.ProductQuantity;
import com.nnmpizza.delivery.payload.request.OrderRequest;
import com.nnmpizza.delivery.repository.MemberRepository;
import com.nnmpizza.delivery.repository.OrderRepository;
import com.nnmpizza.delivery.repository.ProductRepository;
import com.nnmpizza.delivery.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    final OrderRepository orderRepository;
    final MemberRepository memberRepository;
    final ProductRepository productRepository;
    final TransactionRepository transactionRepository;

    public OrderController(OrderRepository orderRepository, MemberRepository memberRepository, ProductRepository productRepository, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest){
        var member = memberRepository.findById(orderRequest.getMemberId());
        if(member.isEmpty())
            return ResponseEntity.ok("Failed to register order");

            var transaction = new Transaction(member.get());
            transactionRepository.save(transaction);

            List<ProductQuantity> requestProducts = orderRequest.getProducts();
            List<Product> products = new ArrayList<>();
            for(var requestProduct: requestProducts) {
                Order order = new Order();
                order.setTransaction(transaction);
                var product = productRepository.findById(requestProduct.getProductId());
                if (product.isEmpty())
                    return ResponseEntity.ok("Failed to register order");


                order.setProduct(product.get());
                order.setQuantity(requestProduct.getQuantity());
                order.setStatus(orderRequest.getStatus());
                orderRepository.save(order);
            }

            return ResponseEntity.ok("Order registered successfully");
    }
}
