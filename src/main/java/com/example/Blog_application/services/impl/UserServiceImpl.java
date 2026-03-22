package com.example.Blog_application.services.impl;

import com.example.Blog_application.entities.Role;
import com.example.Blog_application.entities.User;
import com.example.Blog_application.exceptions.ResourceNotFoundException;
import com.example.Blog_application.payloads.UserDTO;
import com.example.Blog_application.repositories.RoleRepo;
import com.example.Blog_application.repositories.UserRepo;
import com.example.Blog_application.services.UserService;
import com.example.Blog_application.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.ROLE_NORMAL).get();
        user.getRoles().add(role);
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = DTOtoUser(userDTO);
        User savedUser = userRepo.save(user);
        return UsertoDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        return UsertoDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        List<UserDTO> allUsersDTO = allUsers.stream().map(user -> UsertoDTO(user)).collect(Collectors.toList());
        return allUsersDTO;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        User updatedUser = userRepo.save(user);
        UserDTO updatedUserDTO = UsertoDTO(updatedUser);
        return updatedUserDTO;
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepo.delete(user);
    }

    public User DTOtoUser(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
//        User user = new User();
//                user.setId(userDTO.getId());
//                user.setName(userDTO.getName());
//                user.setEmail(userDTO.getEmail());
//                user.setPassword(userDTO.getPassword());
//                user.setAbout(userDTO.getAbout());
        return user;
    }

    public UserDTO UsertoDTO(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//        UserDTO userDTO = new UserDTO();
//                userDTO.setId(user.getId());
//                userDTO.setName(user.getName());
//                userDTO.setEmail(user.getEmail());
//                userDTO.setPassword(user.getPassword());
//                userDTO.setAbout(user.getAbout());
        return userDTO;
    }
}
