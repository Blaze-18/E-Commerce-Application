package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long>{

    Product findByName(String name);
    List<Product> findByNameContainingIgnoreCase(String category);
    List<Product> findByPriceLessThanEqual(double price);
    List<Product> findByPriceGreaterThanEqual(double price);
    List<Product> findByPriceBetween(double low, double high);
    List<Product> findByCategory(String category);
    List<Product> findByRatingAndCategoryContainsIgnoreCaseOrderByRatingAsc(double rating, String name);
    List<Product> findByStockQuantityGreaterThanEqual(int stockQuantity);
    List<Product> findByStockQuantityLessThanEqual(int stockQuantity);
}
