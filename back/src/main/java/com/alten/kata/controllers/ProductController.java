package com.alten.kata.controllers;

import com.alten.kata.model.Product;
import com.alten.kata.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/{userId}/cart/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @PathVariable Long productId) {
        productService.addToCart(userId, productId);
        return ResponseEntity.ok("Produit ajouté au panier");
    }

    @DeleteMapping("/{userId}/cart/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        productService.removeFromCart(userId, productId);
        return ResponseEntity.ok("Produit retiré du panier");
    }

    @PostMapping("/{userId}/wishlist/{productId}")
    public ResponseEntity<String> addToWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        productService.addToWishlist(userId, productId);
        return ResponseEntity.ok("Produit ajouté à la wishlist");
    }

    @DeleteMapping("/{userId}/wishlist/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        productService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok("Produit retiré de la wishlist");
    }
}
