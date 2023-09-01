package com.springproject.blog.Controllers;

import com.springproject.blog.payloads.ApiResponse;
import com.springproject.blog.payloads.CategoryDto;
import com.springproject.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){

        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);

        return ResponseEntity.ok(updatedCategory);
    }


    //delete
    @DeleteMapping("/DeleteCategory{CategoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){

        this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true),HttpStatus.OK);


    }

    //getbyid
    @GetMapping("/getCategory/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){

        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }


    //getall
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        return ResponseEntity.ok(categoryDtoList);
    }

}
