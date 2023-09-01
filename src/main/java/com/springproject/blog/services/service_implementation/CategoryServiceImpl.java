package com.springproject.blog.services.service_implementation;

import com.springproject.blog.Exceptions.ResourceNotFoundException;
import com.springproject.blog.entities.Category;
import com.springproject.blog.payloads.CategoryDto;
import com.springproject.blog.repositories.CategoryRepo;
import com.springproject.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        //map categoryDto to category entity
        Category category = modelMapper.map(categoryDto, Category.class);

        //save the category entity
        Category savedCategory = this.categoryRepo.save(category);

       return this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        //fetching
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category","category Id",categoryId));

        //get and set new data
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription((categoryDto.getCategoryDescription()));

        //saveing
        Category updatedCategory = this.categoryRepo.save(category);

        //returning
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","category Id",categoryId));

        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","category Id",categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryDto> categoryDtosList = new ArrayList<>();

        List<Category> categories = this.categoryRepo.findAll();

        for(Category x: categories){

            CategoryDto categoryDtos = this.modelMapper.map(x, CategoryDto.class);

            categoryDtosList.add(categoryDtos);
        }

        return categoryDtosList;

    }
}
