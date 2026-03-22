package com.example.Blog_application.services.impl;

import com.example.Blog_application.entities.Comment;
import com.example.Blog_application.entities.Post;
import com.example.Blog_application.entities.User;
import com.example.Blog_application.exceptions.ResourceNotFoundException;
import com.example.Blog_application.payloads.CommentDTO;
import com.example.Blog_application.repositories.CommentRepo;
import com.example.Blog_application.repositories.PostRepo;
import com.example.Blog_application.repositories.UserRepo;
import com.example.Blog_application.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, int postId, int userId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepo.save(comment);
        CommentDTO savedCommentDTO = modelMapper.map(savedComment, CommentDTO.class);
        return savedCommentDTO;
    }

    @Override
    public void deleteComment(int id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        commentRepo.delete(comment);
    }
}
