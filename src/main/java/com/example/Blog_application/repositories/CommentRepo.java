package com.example.Blog_application.repositories;

import com.example.Blog_application.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
