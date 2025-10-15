package com.example.griftblog.Controllers;

import com.example.griftblog.Service.CategoryService;
import com.example.griftblog.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/category")

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<Category> createCategory(@RequestBody String name) {
        return ResponseEntity.ok(categoryService.createCategory(name));
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<Category>> getCategoryByslug(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryBySlug(name));
    }

}
