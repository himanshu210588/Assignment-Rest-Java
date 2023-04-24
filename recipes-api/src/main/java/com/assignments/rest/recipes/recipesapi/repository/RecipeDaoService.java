package com.assignments.rest.recipes.recipesapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.customeExceptions.RecipeNotFoundException;

@Component
public class RecipeDaoService {

	// Get logger for this class
	Logger logger = LoggerFactory.getLogger(RecipeDaoService.class);
	
    private RecipesRepository repository;
	
	public RecipeDaoService(RecipesRepository repository) {
		
		this.repository = repository;
	}
	
	
	public Recipe findRecipeById(int recipeId) {
		Optional<Recipe> recipe = repository.findById(recipeId);
		
		// throw custom exception if recipe with given id could not be found
		if(recipe.isEmpty()) {
			String message = "Not Found - Recipe Id : "+recipeId;
			
			logger.error(message);
			throw new RecipeNotFoundException(message);
		}
		return recipe.get();
	}
	
	public Recipe updateRecipeById(int recipeId, Recipe recipeIn) {
		Optional<Recipe> recipeExistingOpt = repository.findById(recipeId);
		
		// throw custom exception if recipe with given id could not be found
		if(recipeExistingOpt.isEmpty()) {
			String message = "Not Found - Recipe Id : "+recipeId;
			
			logger.error(message);
			throw new RecipeNotFoundException(message);
		}
		
		// Fetch the existing recipe
		Recipe recipeExisting = recipeExistingOpt.get();
		
		//Update the existing recipe
		recipeExisting.setRecipeName(recipeIn.getRecipeName() != null ? recipeIn.getRecipeName() : recipeExisting.getRecipeName());
		recipeExisting.setRecipeOwner(recipeIn.getRecipeOwner() != null ? recipeIn.getRecipeOwner() : recipeExisting.getRecipeOwner());
		recipeExisting.setIsVegetarian(recipeIn.getIsVegetarian() != null ? recipeIn.getIsVegetarian() : recipeExisting.getIsVegetarian());
		recipeExisting.setCapacity(recipeIn.getCapacity() != null ? recipeIn.getCapacity() : recipeExisting.getCapacity());
		
		repository.save(recipeExisting);
		
		logger.debug("Updated recipe with recipeId: "+recipeId);
		
		return recipeExisting;
	}
	
	public void deleteById(int recipeId) {
		repository.deleteById(recipeId);
		logger.debug("Deleted recipe with recipeId: "+recipeId);
	}
	
	public Recipe saveRecipe(Recipe recipe) {
		Recipe savedRecipe = repository.save(recipe);
		
		logger.debug("Added recipe with recipeId: "+savedRecipe.getRecipeId());
		return recipe;
	}
	
	// This method fetches all available recipes(in case no query parameter was passed in query string), otherwise filters recipes based on the parameters
	public List<Recipe> getFilteredRecipes(String isVeg, String capacity, String creationTime, List<String> ingredients) {
		List<Recipe> recipes = repository.findAll();
		List<Recipe> filteredRecipes;
		
		logger.debug("Received request for recipes");
		
		// if a parameter is not present in the query string, it will be null
		filteredRecipes = recipes.stream()
						  .filter(recipe -> isVeg == null || recipe.getIsVegetarian().toString().equals(isVeg))
						  .filter(recipe -> capacity == null || recipe.getCapacity().toString().equals(capacity))
						  .filter(recipe -> creationTime == null || recipe.getFormattedCreationDateTime().toString().equals(creationTime))
						  .filter(recipe -> ingredients == null || recipe.getIngredients().containsAll(ingredients))
						  .collect(Collectors.toList());
		
		logger.debug("Retrieved and sent the recipes with requested filters");
		
		return filteredRecipes;
	}
}
