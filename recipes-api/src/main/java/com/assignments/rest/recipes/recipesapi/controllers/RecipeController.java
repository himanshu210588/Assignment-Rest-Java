package com.assignments.rest.recipes.recipesapi.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/recipes")
public class RecipeController {

	
    private RecipeDaoService service;
	
	public RecipeController(RecipeDaoService service) {
		
		this.service = service;
	}	
	
	@GetMapping(path = "/{recipeId}")
	public EntityModel<Recipe> retrieveRecipeById(@PathVariable int recipeId){
		
		// Get recipe by its if using service
		Recipe recipe = service.findRecipeById(recipeId);
		
		// Prepare entity model to capture link to all recipes so users can easily navigate
		EntityModel<Recipe> entityModel = EntityModel.of(recipe);
		
		WebMvcLinkBuilder link =  linkTo(getClass());
		entityModel.add(link.withRel("all-recipes"));
		
		return entityModel;
	}
	
	@PutMapping(path = "/{recipeId}")
	public Recipe updateRecipeById(@PathVariable int recipeId, @Valid @RequestBody Recipe recipeIn){
		return service.updateRecipeById(recipeId, recipeIn);
	}
	
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
		
		// Persist the new recipe
		Recipe persistedRecipe = service.saveRecipe(recipe);
		
		URI createdRecipeLocation = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{recipeId}")
				.buildAndExpand(persistedRecipe.getRecipeId())
				.toUri();
		
		return ResponseEntity.created(createdRecipeLocation).build();
	}
	
	@DeleteMapping(path = "/{recipeId}")
	public void deleteRecipeById(@PathVariable int recipeId){
		service.deleteById(recipeId);
	}
	
	// Retrieve recipes based on query parameters(also when no query parameters are passed)
	@GetMapping
	//@PreAuthorize("hasAnyAuthority('ROLE_USER','SCOPE_ROLE_USER')")
	public List<Recipe> getFilteredRecipes(@RequestParam(name="isVeg",required = false) String isVeg,
											@RequestParam(name="capacity",required = false) String capacity,
											@RequestParam(name="creationTime",required = false) String creationTime,
											@RequestParam(name="ingredients",required = false) List<String> ingredients){
		
		return service.getFilteredRecipes(isVeg, capacity, creationTime,ingredients);
	}
	
	
	
}
