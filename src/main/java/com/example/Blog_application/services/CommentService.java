package com.example.Blog_application.services;

import com.example.Blog_application.payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, int postId, int userId);

    void deleteComment(int id);

}
