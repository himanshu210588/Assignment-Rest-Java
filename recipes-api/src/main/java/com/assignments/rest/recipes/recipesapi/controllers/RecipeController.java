package com.assignments.rest.recipes.recipesapi.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;

import jakarta.validation.Valid;

@RestController
public class RecipeController {

	
    private RecipeDaoService service;
	
	public RecipeController(RecipeDaoService service) {
		
		this.service = service;
	}	
	
	@GetMapping(path = "/recipes/{recipeId}")
	public Recipe retrieveRecipeById(@PathVariable int recipeId){
		return service.findById(recipeId);
	}
	
	@PutMapping(path = "/recipes/{recipeId}")
	public Recipe updateRecipeById(@PathVariable int recipeId, @Valid @RequestBody Recipe recipeIn){
		return service.updateRecipeById(recipeId, recipeIn);
	}
	
	@PostMapping(path = "/recipes")
	public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
		
		// Persist the new recipe
		Recipe persistedRecipe = service.save(recipe);
		
		URI createdRecipeLocation = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{recipeId}")
				.buildAndExpand(persistedRecipe.getRecipeId())
				.toUri();
		
		return ResponseEntity.created(createdRecipeLocation).build();
	}
	
	@DeleteMapping(path = "/recipes/{recipeId}")
	public void deleteRecipeById(@PathVariable int recipeId){
		service.deleteById(recipeId);
	}
	
	// Retrieve recipes based on query parameters
	@GetMapping(path = "/recipes")
	public List<Recipe> getVegetarianDishRecipes(@RequestParam(name="isVeg",required = false) String isVeg,
												@RequestParam(name="capacity",required = false) String capacity,
												@RequestParam(name="creationTime",required = false) String creationTime){
		
		return service.getFilteredRecipes(isVeg, capacity, creationTime);
	}
	
	
	
}
