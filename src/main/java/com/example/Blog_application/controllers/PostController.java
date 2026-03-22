package com.example.Blog_application.controllers;

import com.example.Blog_application.payloads.ApiResponse;
import com.example.Blog_application.payloads.PostDTO;
import com.example.Blog_application.payloads.PostResponse;
import com.example.Blog_application.services.FileService;
import com.example.Blog_application.services.PostService;
import com.example.Blog_application.utils.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable int userId, @PathVariable int categoryId){
        PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> findPostsByCategory(@PathVariable int categoryId,
                                                             @RequestParam(value = "pageNumber", defaultValue = "0", required = false)
                                                                int pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = "5", required = false)
                                                                int pageSize){
        PostResponse postResponse = postService.getPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> findPostsByUser(@PathVariable int userId,
                                                         @RequestParam(value = "pageNumber", defaultValue = "0", required = false)
                                                            int pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
                                                         ){
        PostResponse postResponse = postService.getPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> findPostById(@PathVariable int id){
        PostDTO postById = postService.getPostById(id);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @GetMapping("posts/find-all")
    public ResponseEntity<PostResponse> findAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
                                                          int pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
                                                      int pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY , required = false)
                                                         String sortBy,
                                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @PutMapping("/posts/update/{id}")
    public ResponseEntity<PostDTO> updatePostById(@RequestBody PostDTO postDTO, @PathVariable int id){
        PostDTO updatedPostDTO = postService.updatePost(postDTO, id);
        return new ResponseEntity<>(updatedPostDTO ,HttpStatus.OK);
    }

    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable int id){
        postService.deletePost(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyword){
        List<PostDTO> postDTOs = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);

    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable int postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDTO.setImageName(fileName);
        PostDTO updatedPost = postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
