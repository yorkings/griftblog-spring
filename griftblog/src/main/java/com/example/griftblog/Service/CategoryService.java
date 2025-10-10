package com.example.griftblog.Service;

import com.example.griftblog.Repository.CategoryRepository;
import com.example.griftblog.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepo;

    @Transactional
    public Category createCategory(String name) {
        String slug = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll(" ", "-");

        // Business Logic: Ensure uniqueness before saving
        if (categoryRepo.findByName(name).isPresent() || categoryRepo.findBySlug(slug).isPresent()) {
            throw new IllegalArgumentException("Category name or slug already exists.");
        }

        Category category = Category.builder().name(name).slug(slug).build();
        return categoryRepo.save(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    public Optional<Category> getCategoryBySlug(String slug) {
        return categoryRepo.findBySlug(slug);
    }
}