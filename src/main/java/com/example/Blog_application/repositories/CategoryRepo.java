package com.example.Blog_application.repositories;

import com.example.Blog_application.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
