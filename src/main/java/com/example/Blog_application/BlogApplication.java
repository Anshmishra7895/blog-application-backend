package com.example.Blog_application;

import com.example.Blog_application.entities.Role;
import com.example.Blog_application.repositories.RoleRepo;
import com.example.Blog_application.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			Role adminRole = new Role();
			adminRole.setId(AppConstants.ROLE_ADMIN);
			adminRole.setName("ROLE_ADMIN");

			Role normalRole = new Role();
			normalRole.setId(AppConstants.ROLE_NORMAL);
			normalRole.setName("ROLE_NORMAL");

			List<Role> roles = List.of(adminRole, normalRole);
			List<Role> roleList = roleRepo.saveAll(roles);
			roleList.forEach(role -> {
				System.out.println(role.getName());
			});
		}
		catch (Exception e){

		}
//		System.out.println(passwordEncoder.encode("54321"));
	}
}
