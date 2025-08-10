package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // Add methods to interact with the productRepository as needed for business logic

    public void addProduct(Product product) {
        productRepository.save(product);
    }
    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public void updateProduct(Product productDetails, Long id){
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
            existingProduct.setCategory(productDetails.getCategory());
            existingProduct.setRating(productDetails.getRating());
            existingProduct.setUpdatedAt(productDetails.getUpdatedAt());
            productRepository.save(existingProduct);
        }else{
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public List<Product> searchByName(String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Product> getProductsByPriceLessThanEqual(double price) {
        return productRepository.findByPriceLessThanEqual(price);
    }
    public List<Product> getProductsByPriceGreaterThanEqual(double price) {
        return productRepository.findByPriceGreaterThanEqual(price);
    }
    public List<Product> getProductsByPriceBetween(double low, double high) {
        return productRepository.findByPriceBetween(low, high);
    }
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    public List<Product> getProductsByRatingAndName(double rating, String name) {
        return productRepository.findByRatingAndNameIgnoreCaseOrderByRatingAsc(rating, name);
    }

    public List<Product> getProductsByStockQuantityGreaterThanEqual(int stockQuantity) {
        return productRepository.findByStockQuantityGreaterThanEqual(stockQuantity);
    }
    public List<Product> getProductsByStockQuantityLessThanEqual(int stockQuantity) {
        return productRepository.findByStockQuantityLessThanEqual(stockQuantity);
    }
}
