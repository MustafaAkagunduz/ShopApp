package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.ProductRequest;
import com.noisy.rrssProject.model.dto.response.ProductPageResponse;
import com.noisy.rrssProject.model.dto.response.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Product;
import com.noisy.rrssProject.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/pages")
    public ResponseEntity<ProductPageResponse>
    getAllProductsPaginated(
            @RequestParam(value = "page", required = false,defaultValue = "0") int page
            , @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(
                productService.getPaginatedAll(page, size)
        );
    }

    @GetMapping("/all/category/{categoryName}")
    public ResponseEntity<ProductPageResponse> getAllProductsByCategoryName(
            @RequestParam(value = "page", required = false,defaultValue = "0") int page
            , @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @PathVariable String categoryName) {
        ProductPageResponse responseList = productService.getPaginatedByCategoryName(page, size, categoryName);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all/merchant/{merchantName}")
    public ResponseEntity<ProductPageResponse> getAllProductsByMerchantName(
            @RequestParam(value = "page", required = false,defaultValue = "0") int page
            , @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ,@RequestParam String merchantName) {
        ProductPageResponse responseList = productService.getPaginatedByMerchantName(page,size,merchantName);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
        ProductResponse updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
