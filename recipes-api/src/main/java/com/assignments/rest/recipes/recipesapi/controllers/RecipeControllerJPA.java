package com.assignments.rest.recipes.recipesapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.customeExceptions.RecipeNotFoundException;
import com.assignments.rest.recipes.recipesapi.repository.RecipesRepository;

import jakarta.validation.Valid;

@RestController
public class RecipeControllerJPA {

	
	private RecipesRepository repository;
	
	public RecipeControllerJPA(RecipesRepository repository) {
		
		this.repository = repository;
	}
	
	
	@GetMapping(path = "/recipes-all")
	public List<Recipe> retrieveAllRecipes(){
		return repository.findAll();
	}
	
	@GetMapping(path = "/recipes/{recipeId}")
	public Recipe retrieveRecipeById(@PathVariable int recipeId){
		Optional<Recipe> recipe = repository.findById(recipeId);
		
		// throw custom exception if recipe with given id could not be found
		if(recipe.isEmpty()) {
			throw new RecipeNotFoundException("Not Found - Recipe Id : "+recipeId);
		}
		
		return recipe.get();
	}
	
	@PostMapping(path = "/recipes")
	public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
		
		// Persist the new recipe
		Recipe persistedRecipe = repository.save(recipe);
		
		URI createdRecipeLocation = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{recipeId}")
				.buildAndExpand(persistedRecipe.getRecipeId())
				.toUri();
		
		return ResponseEntity.created(createdRecipeLocation).build();
	}
	
	@DeleteMapping(path = "/recipes/{recipeId}")
	public void deleteRecipeById(@PathVariable int recipeId){
		repository.deleteById(recipeId);
	}
	
	// Retrieve recipes based on query parameters
	@GetMapping(path = "/recipes")
	public List<Recipe> getVegetarianDishRecipes(@RequestParam(name="isVeg",required = false) String isVeg,
												@RequestParam(name="capacity",required = false) String capacity,
												@RequestParam(name="creationTime",required = false) String creationTime){
		
		System.out.println("****** HIT ******"+isVeg);
		List<Recipe> filteredRecipes = repository.findAll();
		
		if(isVeg != null) {
			Boolean isVegetarian = Boolean.parseBoolean(isVeg);
			filteredRecipes = repository.findByIsVegetarian(isVegetarian);
		}
		if(capacity != null) {
			Predicate<? super Recipe> predicate = recipe -> recipe.getCapacity().toString().equals(capacity);
			filteredRecipes = filteredRecipes.stream().filter(predicate).toList();
		}if(creationTime != null) {
			Predicate<? super Recipe> predicate = recipe -> recipe.getFormattedCreationDateTime().toString().equals(creationTime);
			filteredRecipes = filteredRecipes.stream().filter(predicate).toList();
		}
		
		return filteredRecipes;
	}
	
	
	
}
