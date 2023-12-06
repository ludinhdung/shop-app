package com.shopapp.controllers;

import com.shopapp.dtos.CategoryDto;
import com.shopapp.mapper.CategoryMapper;
import com.shopapp.models.Category;
import com.shopapp.response.CategoryResponse;
import com.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {

        return categoryService.getPage(page, limit).stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        Category savedCategory = categoryService.create(categoryMapper.toEntity(categoryDto));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CategoryResponse("Create category success", savedCategory, null));
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id) {

        Category foundCategory = categoryService.byId(id);

        return new CategoryResponse("Get category success", foundCategory, null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CategoryResponse deleteCategory(@PathVariable Integer id) {

        categoryService.delete(id);

        return new CategoryResponse("Delete category success", null, null);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDto categoryDto) {

        Category updatedCategory = categoryService.update(id, categoryMapper.toEntity(categoryDto));

        return new CategoryResponse("Update category success", updatedCategory, null);
    }
}
