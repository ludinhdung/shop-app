package com.shopapp.services;

import com.shopapp.exceptions.CategoryNotFoundException;
import com.shopapp.models.Category;
import com.shopapp.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void whenGetAll_thenReturnListCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Electronic"));
        categories.add(new Category(2, "Toy"));

        given(categoryRepository.findAll(PageRequest.of(0, 10))).willReturn(new PageImpl<>(categories));

        List<Category> returnCategories = categoryService.getPage(0, 10);

        assertThat(returnCategories.size()).isEqualTo(2);
        assertThat(returnCategories.get(0).getName()).isEqualTo("Electronic");
        assertThat(returnCategories.get(1).getName()).isEqualTo("Toy");
    }

    @Test
    void givenCategory_whenCreateCategory_thenReturnSavedCategory() {
        Category savedCategory = new Category(1, "Toy");

        given(categoryRepository.save(any(Category.class))).willReturn(savedCategory);

        assertThat(categoryService.create(savedCategory)).isNotNull();
        assertThat(categoryService.create(savedCategory).getName()).isEqualTo("Toy");
    }

    @Test
    void givenId_whenGetCategory_thenReturnFoundCategory() {
        Category foundCategory = new Category(1, "Toy");

        given(categoryRepository.findById(anyInt())).willReturn(Optional.of(foundCategory));

        assertThat(categoryService.byId(1)).isNotNull();
        assertThat(categoryService.byId(1).getName()).isEqualTo("Toy");
    }

    @Test
    void givenNonExistId_whenGetCategory_thenThrowException() {
        given(categoryRepository.findById(anyInt())).willReturn(Optional.empty());

        Throwable exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.byId(1));

        assertEquals("The category with id 1 was not not found", exception.getMessage());
    }

    @Test
    void givenId_whenDeleteCategory_thenVerifiedDelete() {
        Category foundCategory = new Category(1, "Toy");

        given(categoryRepository.findById(anyInt())).willReturn(Optional.of(foundCategory));
        doNothing().when(categoryRepository).deleteById(anyInt());

        categoryService.delete(1);
        verify(categoryRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void givenNonExistId_whenDeleteCategory_thenThrowException() {
        given(categoryRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () ->
                categoryService.delete(1)
        );
    }

    @Test
    void givenIdAndCategory_whenUpdateCategory_thenReturnUpdatedCategory() {
        Category foundCategory = new Category(1, "Toy");
        Category updatedCategory = new Category(1, "Electrics");

        given(categoryRepository.findById(anyInt())).willReturn(Optional.of(foundCategory));
        given(categoryRepository.save(any(Category.class))).willReturn(updatedCategory);

        assertThat(categoryService.update(1, updatedCategory)).isNotNull();
        assertThat(categoryService.update(1, updatedCategory).getName()).isEqualTo("Electrics");
    }

    @Test
    void givenNonExistId_whenUpdateCategory_thenThrowException() {
        Category updatedCategory = new Category(1, "Electrics");

        given(categoryRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () ->
                categoryService.update(1, updatedCategory)
        );
    }
}