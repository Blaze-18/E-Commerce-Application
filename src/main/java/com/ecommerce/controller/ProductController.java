package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // Add methods to handle HTTP requests and interact with the productService as needed

    @PostMapping("/add")
    public void addProduct(@Valid @RequestBody Product product) {
        productService.addProduct(product);
    }

    @PutMapping("/update/{id}")
    public void updateProduct(@Valid @RequestBody Product productDetails, @PathVariable Long id) {
        productService.updateProduct(productDetails, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @GetMapping
    public List<Product> findAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/search")
    public List<Product> searchByName(@Valid @RequestParam String name){
        return productService.searchByName(name);
    }
    @GetMapping("/price/ltq")
    public List<Product> getProductsByPriceLessThanEqual(@Valid @RequestParam double price){
        return productService.getProductsByPriceLessThanEqual(price);
    }
    @GetMapping("/price/gtq")
    public List<Product> getProductsByPriceGreaterThanEqual(@Valid @RequestParam double price) {
        return productService.getProductsByPriceGreaterThanEqual(price);
    }
    @GetMapping("/price/btw")
    public List<Product> getProductsByPriceBetween(@Valid @RequestParam double low,@Valid @RequestParam double high){
        return productService.getProductsByPriceBetween(low, high);
    }
    @GetMapping("/category")
    public List<Product> getProductByCategory(@Valid @RequestParam String category){
        return productService.getProductsByCategory(category);
    }
    @GetMapping("/rating")
    public List<Product>  getProductByRatingAndCategory(@Valid @RequestParam double rating, String category){
        return productService.getProductsByRatingAndCategory(rating, category);
    }

    @GetMapping("/stock/gtq")
    public List<Product> getProductsByStockQuantityGreaterThanEqual(@Valid @RequestParam int stockQuantity){
        return productService.getProductsByStockQuantityGreaterThanEqual(stockQuantity);
    }
    @GetMapping("/stock/ltq")
    public List<Product> getProductsByStockQuantityLessThanEqual(@Valid @RequestParam int stockQuantity){
        return productService.getProductsByStockQuantityLessThanEqual(stockQuantity);
    }
}
