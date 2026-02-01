package com.example.springbootmybatispostgres.entities;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Min(value = 1, message = "Page count must be at least 1")
    private int page;

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    private String isbn;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;
}