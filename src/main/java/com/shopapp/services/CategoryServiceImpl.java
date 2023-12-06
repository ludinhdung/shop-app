package com.shopapp.services;

import com.shopapp.exceptions.CategoryNotFoundException;
import com.shopapp.models.Category;
import com.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_PAGE = 0;

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = buildPageRequest(pageNumber, pageSize);
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category byId(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        categoryRepository.deleteById(id);
    }

    @Override
    public Category update(Integer id, Category category) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        foundCategory.setName(category.getName());
        return categoryRepository.save(foundCategory);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else queryPageNumber = DEFAULT_PAGE;

        if (pageSize == null) {
            queryPageSize = DEFAULT_SIZE;
        } else {
            if (pageSize > 100) queryPageSize = 100;
            else queryPageSize = pageSize;
        }
        return PageRequest.of(queryPageNumber, queryPageSize);
    }
}
