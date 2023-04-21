package com.assignments.rest.recipes.recipesapi.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
}
