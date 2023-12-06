package com.shopapp.response;

import com.shopapp.models.Category;

import java.util.List;

public record CategoryResponse(String message, Category data, List<String> errors) {
}
