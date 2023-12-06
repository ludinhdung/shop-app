package com.shopapp.services;

import com.shopapp.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getPage(Integer page, Integer limit);

    Category create(Category category);

    Category byId(Integer id);

    void delete(Integer id);

    Category update(Integer id, Category category);
}
