package com.example.Blog_application.services;

import com.example.Blog_application.payloads.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Integer id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO, Integer id);

    void deleteUserById(Integer id);
}
