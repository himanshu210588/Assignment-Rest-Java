package com.assignments.rest.recipes.recipesapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;

public interface RecipesRepository extends JpaRepository<Recipe,Integer>{

	List<Recipe> findByIsVegetarian(boolean isVegetarian);
	List<Recipe> findByCapacity(Integer capacity);
}
