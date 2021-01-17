package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Topping;
import com.nnmpizza.delivery.payload.request.ProductRequest;
import com.nnmpizza.delivery.payload.request.ToppingRequest;
import com.nnmpizza.delivery.repository.ToppingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

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
        return toppingRepository.findAll();
    }

    @GetMapping("/search/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> paginateTopping(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                             @RequestParam(value = "perPage", defaultValue = "10000") int perPage,
                                             @RequestParam String type){

        Pageable pageRequest = PageRequest.of(currentPage-1, perPage);
        Page<Topping> Toppings = toppingRepository.findPageToppings(pageRequest, type.toLowerCase());

        Map<String, Object> response = new HashMap<>();
        response.put("toppings", Toppings.getContent());
        response.put("currentPage", Toppings.getNumber()+1);
        response.put("totalItems", Toppings.getTotalElements());
        response.put("totalPages", Toppings.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delTopping(@RequestParam Long id){
        if(toppingRepository.existsById(id)) {
            toppingRepository.deleteById(id);
            return ResponseEntity.ok("Успешно изтрит");
        }
        return ResponseEntity.ok("Няма topping с това ID.");
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveTopping(@RequestBody ToppingRequest toppingRequest) {
        boolean isNew = toppingRequest.getId() == null;

        Topping newTopping = new Topping(toppingRequest.getId(), toppingRequest.getName(), toppingRequest.getType());

        toppingRepository.save(newTopping);

        Map<String, Object> response = new HashMap<>();
        response.put("topping", newTopping);
        if (!isNew) {
            response.put("message", "Успешно редактиран.");
        } else {
            response.put("message", "Успешно записан.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
