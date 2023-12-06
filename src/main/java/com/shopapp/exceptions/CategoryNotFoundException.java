package com.shopapp.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Integer id) {
        super(String.format("The category with id %d was not not found", id));
    }
}
