package com.assignments.rest.recipes.recipesapi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;

@Component
public class RecipeDaoService {

	private static List<Recipe> recipes = new ArrayList<>();
	
	private static int recipeCount = 0;
	
	static {
		recipes.add(new Recipe(++recipeCount,"Dosa","Satya",true,4));
		recipes.add(new Recipe(++recipeCount,"Pav Bhaji","Akash",true,6));
		recipes.add(new Recipe(++recipeCount,"Butter Chicken","Najar",false,4));
		recipes.add(new Recipe(++recipeCount,"Mango Shake","Jagrati",true,2));
	}
	
	public List<Recipe> findAll(){
		return recipes;
	}
	
	public Recipe findOne(int recipeId) {
		Predicate<? super Recipe> predicate = recipe -> recipe.getRecipeId().equals(recipeId);
		return recipes.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteById(int recipeId) {
		Predicate<? super Recipe> predicate = recipe -> recipe.getRecipeId().equals(recipeId);
		recipes.removeIf(predicate);
	}
	
	public Recipe save(Recipe recipe) {
		recipe.setRecipeId(++recipeCount);
		recipes.add(recipe);
		return recipe;
	}
	
	public List<Recipe> filterByVeg(String isVeg, String creationTime) {
		Predicate<? super Recipe> predicate = recipe -> recipe.getIsVegetarian().toString().equals(isVeg) && recipe.getFormattedCreationDateTime().equals(creationTime);
		return recipes.stream().filter(predicate).toList();
	}
}
