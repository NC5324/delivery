package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.models.Topping;
import com.nnmpizza.delivery.payload.request.ProductRequest;
import com.nnmpizza.delivery.payload.request.ToppingRequest;
import com.nnmpizza.delivery.repository.ProductRepository;
import com.nnmpizza.delivery.repository.ToppingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {

    final ProductRepository ProductRepository;
    final ToppingRepository toppingRepository;

    public ProductController(ProductRepository ProductRepository, ToppingRepository toppingRepository){
        this.ProductRepository = ProductRepository;
        this.toppingRepository = toppingRepository;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return ProductRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest) {
        boolean isNew = productRequest.getId() == null;

        Product newProduct = new Product(productRequest.getId(), productRequest.getName(), productRequest.getType(), productRequest.getPrice(), productRequest.getImgSource());

        Set<ToppingRequest> jsonToppings = productRequest.getToppings();
        Set<Topping> toppings = new HashSet<>();
        for (var jsonTopping : jsonToppings) {
            if (toppingRepository.findById(jsonTopping.getId()).isPresent()) {
                toppings.add(toppingRepository.findById(jsonTopping.getId()).get());
            }
        }

        newProduct.setToppings(toppings);
        ProductRepository.save(newProduct);

        Map<String, Object> response = new HashMap<>();
        response.put("Product", newProduct);
        if (!isNew) {
            response.put("message", "Успешно редактиран.");
        } else {
            response.put("message", "Успешно записан.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/id")
    public ResponseEntity<?> getProductById(@RequestParam Long id) {
        Optional<Product> result = ProductRepository.findById(id);

        if(result.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("product", result.get());
            response.put("id", result.get().getId());
            response.put("name", result.get().getName());
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.ok("Product not found");
        }
    }

    @GetMapping("/search/page")
    public ResponseEntity<?> paginateProduct(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                            @RequestParam(value = "perPage", defaultValue = "5") int perPage,
                                            @RequestParam String type){

        Pageable pageRequest = PageRequest.of(currentPage-1, perPage);
        Page<Product> Products = ProductRepository.findPageProducts(pageRequest, type.toLowerCase());

        Map<String, Object> response = new HashMap<>();
        response.put("products", Products.getContent());
        response.put("currentPage", Products.getNumber()+1);
        response.put("totalItems", Products.getTotalElements());
        response.put("totalPages", Products.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delProduct(@RequestParam Long id){
        if(ProductRepository.existsById(id)) {
            ProductRepository.deleteById(id);
            return ResponseEntity.ok("Успешно изтрит");
        }
        return ResponseEntity.ok("Няма човек с това ID.");
    }


}
