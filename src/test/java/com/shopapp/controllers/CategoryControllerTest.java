package com.shopapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopapp.dtos.CategoryDto;
import com.shopapp.exceptions.CategoryNotFoundException;
import com.shopapp.mapper.CategoryMapper;
import com.shopapp.models.Category;
import com.shopapp.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryMapper categoryMapper;

    @Test
    void whenGetPageContent_thenResponseIsOk() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Electronic"));
        categories.add(new Category(2, "Toy"));

        given(categoryService.getPage(0, 10)).willReturn(categories);

        mockMvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void givenCategory_whenCreateCategory_thenResponseIsCreated() throws Exception {
        //given
        CategoryDto categoryDto = new CategoryDto("Toy");
        String json = objectMapper.writeValueAsString(categoryDto);

        Category savedCategory = new Category(1, "Toy");

        given(categoryService.create(any(Category.class))).willReturn(savedCategory);

        //when and then
        mockMvc.perform(post("/api/v1/categories").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Create category success")));
    }

    @Test
    void givenId_whenGetCategory_thenResponseIsOk() throws Exception {
        //given
        Category foundCategory = new Category(1, "Toy");

        given(categoryService.byId(any(Integer.class))).willReturn(foundCategory);

        //when and then
        mockMvc.perform(get("/api/v1/categories/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Get category success")))
                .andExpect(jsonPath("$.data.name", is("Toy")));
    }

    @Test
    void givenNonExistId_whenGetCategory_thenResponseIsNotFound() throws Exception {
        //given
        doThrow(new CategoryNotFoundException(1)).when(categoryService).byId(any(Integer.class));

        //when and then
        mockMvc.perform(get("/api/v1/categories/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("The category with id 1 was not not found")));
    }

    @Test
    void givenId_whenDeleteCategory_thenResponseIsNoContent() throws Exception {
        //when and then
        mockMvc.perform(delete("/api/v1/categories/{id}", 1))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message", is("Delete category success")));
    }

    @Test
    void givenInvalidInput_whenRequestBody_thenResponseIsBadRequest() throws Exception {

        CategoryDto categoryDto = new CategoryDto(null);
        String json = objectMapper.writeValueAsString(categoryDto);

        Category savedCategory = new Category(1, "Toy");

        given(categoryService.create(any(Category.class))).willReturn(savedCategory);

        mockMvc.perform(post("/api/v1/categories").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}