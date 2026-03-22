package com.example.Blog_application.services;

import com.example.Blog_application.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(int id);

    List<CategoryDTO> getAllCategory();

    CategoryDTO updateCategory(CategoryDTO categoryDTO, int id);

    void deleteCategory(int id);

}
