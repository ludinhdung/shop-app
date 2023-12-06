package com.shopapp.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record CategoryDto(@NotNull @Size(max = 100) String name) implements Serializable {
}