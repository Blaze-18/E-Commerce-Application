package com.ecommerce.service;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category createCategory(Category category) {
        if(categoryRepository.existsByName(category.getName())){
            throw new IllegalStateException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }
    @Transactional
    public Category updateCategory(Long id, Category newCategory) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if(existingCategory == null){
            throw new IllegalStateException("Category with id " + id + " does not exist");
        }
        existingCategory.setName(newCategory.getName());
        existingCategory.setDescription(newCategory.getDescription());
        existingCategory.setUpdatedAt(LocalDateTime.now());

        return newCategory;
    }
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new IllegalStateException("Category with id " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }

    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category with id " + id + " does not exist"));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

}
