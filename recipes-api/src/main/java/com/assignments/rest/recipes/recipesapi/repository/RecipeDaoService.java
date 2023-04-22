package com.assignments.rest.recipes.recipesapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.customeExceptions.RecipeNotFoundException;

@Component
public class RecipeDaoService {

	
    private RecipesRepository repository;
	
	public RecipeDaoService(RecipesRepository repository) {
		
		this.repository = repository;
	}
	
	
	public Recipe findById(int recipeId) {
		Optional<Recipe> recipe = repository.findById(recipeId);
		
		// throw custom exception if recipe with given id could not be found
		if(recipe.isEmpty()) {
			throw new RecipeNotFoundException("Not Found - Recipe Id : "+recipeId);
		}
		
		return recipe.get();
	}
	
	public Recipe updateRecipeById(int recipeId, Recipe recipeIn) {
		Optional<Recipe> recipeExistingOpt = repository.findById(recipeId);
		
		// throw custom exception if recipe with given id could not be found
		if(recipeExistingOpt.isEmpty()) {
			throw new RecipeNotFoundException("Not Found - Recipe Id : "+recipeId);
		}
		
		// Fetch the existing recipe
		Recipe recipeExisting = recipeExistingOpt.get();
		
		//Update the existing recipe
		recipeExisting.setRecipeName(recipeIn.getRecipeName() != null ? recipeIn.getRecipeName() : recipeExisting.getRecipeName());
		recipeExisting.setRecipeOwner(recipeIn.getRecipeOwner() != null ? recipeIn.getRecipeOwner() : recipeExisting.getRecipeOwner());
		recipeExisting.setIsVegetarian(recipeIn.getIsVegetarian() != null ? recipeIn.getIsVegetarian() : recipeExisting.getIsVegetarian());
		recipeExisting.setCapacity(recipeIn.getCapacity() != null ? recipeIn.getCapacity() : recipeExisting.getCapacity());
		
		repository.save(recipeExisting);
		
		return recipeExisting;
	}
	
	public void deleteById(int recipeId) {
		repository.deleteById(recipeId);
	}
	
	public Recipe save(Recipe recipe) {
		return repository.save(recipe);
	}
	
	public List<Recipe> getFilteredRecipes(String isVeg, String capacity, String creationTime) {
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
