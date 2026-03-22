package com.example.Blog_application.repositories;

import com.example.Blog_application.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
