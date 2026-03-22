package com.example.Blog_application.services;

import com.example.Blog_application.entities.Post;
import com.example.Blog_application.payloads.PostDTO;
import com.example.Blog_application.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, int userId, int categoryId);

    PostDTO updatePost(PostDTO postDTO, int id);

    void deletePost(int id);

    PostDTO getPostById(int id);

    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize);

    PostResponse getPostsByUser(int userId, int pageNumber, int pageSize);

    List<PostDTO> searchPosts(String keyword);

}
