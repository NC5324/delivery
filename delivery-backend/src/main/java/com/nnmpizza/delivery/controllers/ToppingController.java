package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Topping;
import com.nnmpizza.delivery.repository.ToppingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/topping")
public class ToppingController {

    final ToppingRepository toppingRepository;

    public ToppingController(ToppingRepository toppingRepository) {
        this.toppingRepository = toppingRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Topping> getAllToppings(){
        //toppings.sort(Comparator.comparing(Topping::getName));
        return toppingRepository.findAll();
    }
}
