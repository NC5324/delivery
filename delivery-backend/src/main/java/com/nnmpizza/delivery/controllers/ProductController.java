package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.payload.request.ProductRequest;
import com.nnmpizza.delivery.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {

    final ProductRepository ProductRepository;

    public ProductController (ProductRepository ProductRepository){
        this.ProductRepository = ProductRepository;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return ProductRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest){
        boolean isNew = productRequest.getId() == null;

        Product newProduct = new Product(productRequest);

        ProductRepository.save(newProduct);

        Map<String, Object> response = new HashMap<>();
        response.put("Product", newProduct);
        if(!isNew){
            response.put("message", "Успешно редактиран.");
        } else {
            response.put("message", "Успешно записан.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/id")
    public ResponseEntity<?> getProductById(@RequestParam Long id) {
        Optional<Product> result = ProductRepository.findById(id);

        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.ok().body("Няма намерен човек.");
    }

    @GetMapping("/search/page")
    public ResponseEntity<?> paginateProduct(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                            @RequestParam(value = "perPage", defaultValue = "5") int perPage,
                                            @RequestParam String name,
                                            @RequestParam String type){

        Pageable pageRequest = PageRequest.of(currentPage-1, perPage);
        Page<Product> Products = ProductRepository.findPageProducts(pageRequest, name.toLowerCase(), type.toLowerCase());

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
