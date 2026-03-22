package com.example.Blog_application.services.impl;

import com.example.Blog_application.entities.Category;
import com.example.Blog_application.entities.Post;
import com.example.Blog_application.entities.User;
import com.example.Blog_application.exceptions.ResourceNotFoundException;
import com.example.Blog_application.payloads.PostDTO;
import com.example.Blog_application.payloads.PostResponse;
import com.example.Blog_application.repositories.CategoryRepo;
import com.example.Blog_application.repositories.PostRepo;
import com.example.Blog_application.repositories.UserRepo;
import com.example.Blog_application.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, int userId, int categoryId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, int id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public void deletePost(int id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }

    @Override
    public PostDTO getPostById(int id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> allPostPages = postRepo.findAll(pageable);
        List<Post> allPost = allPostPages.getContent();
        List<PostDTO> allPostDTOs= allPost.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPostDTOs);
        postResponse.setPageNumber(allPostPages.getNumber());
        postResponse.setPageSize(allPostPages.getSize());
        postResponse.setTotalElements(allPostPages.getTotalElements());
        postResponse.setTotalPages(allPostPages.getTotalPages());
        postResponse.setLastPage(allPostPages.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Page<Post> postsByCategoryPage = postRepo.findByCategory(category, pageable);
        List<Post> postsByCategory = postsByCategoryPage.getContent();
        List<PostDTO> postsDTOByCategory = postsByCategory.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postsDTOByCategory);
        postResponse.setPageNumber(postsByCategoryPage.getNumber());
        postResponse.setPageSize(postsByCategoryPage.getSize());
        postResponse.setTotalElements(postsByCategoryPage.getTotalElements());
        postResponse.setTotalPages(postsByCategoryPage.getTotalPages());
        postResponse.setLastPage(postsByCategoryPage.isLast());
        return postResponse;
//        return postsDTOByCategory;
    }

    @Override
    public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        Page<Post> postsByUserPage = postRepo.findByUser(user, pageable);
        List<Post> postsByUser = postsByUserPage.getContent();
        List<PostDTO> postsDTOByUser = postsByUser.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postsDTOByUser);
        postResponse.setPageNumber(postsByUserPage.getNumber());
        postResponse.setPageSize(postsByUserPage.getSize());
        postResponse.setTotalElements(postsByUserPage.getTotalElements());
        postResponse.setTotalPages(postsByUserPage.getTotalPages());
        postResponse.setLastPage(postsByUserPage.isLast());
        return postResponse;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> postsContainingKeyword = postRepo.findByTitleContaining(keyword);
        List<PostDTO> postDTOscontainingkeyword = postsContainingKeyword.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDTOscontainingkeyword;
    }
}
