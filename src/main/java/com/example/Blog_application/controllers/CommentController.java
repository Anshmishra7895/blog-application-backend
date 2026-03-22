package com.example.Blog_application.controllers;


import com.example.Blog_application.payloads.ApiResponse;
import com.example.Blog_application.payloads.CommentDTO;
import com.example.Blog_application.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable int postId, @PathVariable int userId) {
        CommentDTO savedCommentDTO = commentService.createComment(commentDTO, postId, userId);
        return new ResponseEntity<>(savedCommentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully !!", true), HttpStatus.OK);
    }

}
