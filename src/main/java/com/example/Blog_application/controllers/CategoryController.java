package com.example.Blog_application.controllers;

import com.example.Blog_application.payloads.ApiResponse;
import com.example.Blog_application.payloads.CategoryDTO;
import com.example.Blog_application.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable int id){
        CategoryDTO categoryDTO = categoryService.getCategory(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<CategoryDTO>> findAllCategories(){
        List<CategoryDTO> allCategoryDTO = categoryService.getAllCategory();
        return new ResponseEntity<>(allCategoryDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@Valid  @RequestBody CategoryDTO categoryDTO, @PathVariable int id){
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully !!", true) ,HttpStatus.OK);
    }



}
