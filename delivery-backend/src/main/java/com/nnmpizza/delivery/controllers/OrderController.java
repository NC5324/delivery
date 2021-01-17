package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.MemberNoAuthDetails;
import com.nnmpizza.delivery.models.Order;
import com.nnmpizza.delivery.models.OrderItem;
import com.nnmpizza.delivery.payload.beans.OrderItemBean;
import com.nnmpizza.delivery.payload.request.OrderRequest;
import com.nnmpizza.delivery.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberNoAuthDetailsRepository memberRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository, MemberNoAuthDetailsRepository memberRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest) {
        //Check if member exists
        var member = memberRepository.findById(orderRequest.getMemberId());
        if(member.isEmpty()){
            return ResponseEntity.badRequest().body("Cannot associate order with a nonexistent member!");
        }

        //Check if the order is a new order
        boolean isNew = orderRequest.getId() == null;

        //Create the order
        Order order = new Order(orderRequest.getId(), member.get(), orderRequest.getStatus());

        //If order has empty array of OrderItems return a bad request
        Set<OrderItemBean> jsonOrderItems = orderRequest.getOrderItems();
        if(jsonOrderItems.size() <= 0){
            return ResponseEntity.badRequest().body("Order is empty!");
        }

        //Map every JSON OrderItem to Java OrderItem
        Set<OrderItem> orderItems = new HashSet<>();
        for(var jsonOrderItem : jsonOrderItems) {
            var product = productRepository.findById(jsonOrderItem.getProduct().getId());
            if(product.isEmpty()){
                continue;
            }
            var orderItem = orderItemRepository
                    .findByProductAndQuantity(jsonOrderItem.getProduct().getId(), jsonOrderItem.getQuantity())
                    .orElse(new OrderItem(jsonOrderItem.getId(), product.get(), jsonOrderItem.getQuantity()));
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        //Set the OrderItems of the processed order
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        member.get().getOrders().add(order);
        memberRepository.save(member.get());

        //Return a message depending on the operation performed
        return ResponseEntity.ok(isNew ? "Accepted new order." : "Edited existing order.");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> deleteOrderById(@RequestParam Long id){
        var order = orderRepository.findById(id).get();
        var member = order.getMember();
        member.getOrders().remove(order);
        memberRepository.save(member);
        orderRepository.deleteById(id);

        return ResponseEntity.ok("Deleted successfully.");
    }
}