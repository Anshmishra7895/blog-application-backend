package com.example.Blog_application.repositories;

import com.example.Blog_application.entities.Category;
import com.example.Blog_application.entities.Post;
import com.example.Blog_application.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    Page<Post> findByCategory(Category category, Pageable pageable);

    Page<Post> findByUser(User user, Pageable pageable);

    List<Post> findByTitleContaining(String keyword);
}
