package com.shopapp.exceptions;

import com.shopapp.response.CategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(binding -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(binding.getField(), binding.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CategoryResponse handleCategoryNotFoundException(CategoryNotFoundException ex) {

        return new CategoryResponse(ex.getMessage(), null, null);
    }


}
