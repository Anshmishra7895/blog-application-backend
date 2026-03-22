package com.example.Blog_application.controllers;

import com.example.Blog_application.payloads.ApiResponse;
import com.example.Blog_application.payloads.UserDTO;
import com.example.Blog_application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUserDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserDTO> findUser(@PathVariable int id){
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<UserDTO>> findAllUsers(){
        List<UserDTO> allUsersDTO = userService.getAllUsers();
        return new ResponseEntity<>(allUsersDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUserById(@Valid @PathVariable int id, @RequestBody UserDTO userDTO){
        UserDTO updatedUserDTO = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

}
