package com.example.Blog_application.services.impl;

import com.example.Blog_application.entities.Category;
import com.example.Blog_application.exceptions.ResourceNotFoundException;
import com.example.Blog_application.payloads.CategoryDTO;
import com.example.Blog_application.repositories.CategoryRepo;
import com.example.Blog_application.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategory(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> allCategories = categoryRepo.findAll();
        List<CategoryDTO> allCategoryDTOs = allCategories.stream().map((category) -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        return allCategoryDTOs;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        categoryRepo.delete(category);
    }

//    private Category DTOtoCategory(CategoryDTO categoryDTO){
//        Category category = modelMapper.map(categoryDTO, Category.class);
//        return category;
//    }
//
//    private CategoryDTO CategorytoDTO(Category category){
//        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
//        return categoryDTO;
//    }
}
