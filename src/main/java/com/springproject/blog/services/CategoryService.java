package com.springproject.blog.services;

import com.springproject.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create a category
    CategoryDto createCategory(CategoryDto categoryDto);

    //update category
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //delete category
    void deleteCategory(Integer categoryId);

    //get categoris
    CategoryDto getCategory(Integer categoryId);

    //get All Categories
     List<CategoryDto> getCategories();

}
