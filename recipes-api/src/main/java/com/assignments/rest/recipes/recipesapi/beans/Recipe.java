package com.assignments.rest.recipes.recipesapi.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Recipe {
    
	@Id
	@GeneratedValue
	private Integer recipeId;
	
	@Size(max=20, message = "Name should be max 20 characters long")
	private String recipeName;
	
	@Size(max=20, message = "recipeOwner name should be max 20 characters long")
	private String recipeOwner;
	
	
	private Boolean isVegetarian;
	
	@JsonProperty("creationTime")
	private String formattedCreationDateTime;
	
	@Min(1)
	@Max(10)
	private Integer capacity;
	
	private List<String> ingredients;
	
	public Recipe() {
		setFormattedCreationDateTime();
	}
	
	public Recipe(Integer recipeId, String recipeName, String recipeOwner, Boolean isVegetarian,
			Integer capacity, List<String> ingredients) {
		super();
		this.recipeId = recipeId;
		this.recipeName = recipeName;
		this.recipeOwner = recipeOwner;
		this.isVegetarian = isVegetarian;
		this.capacity = capacity;
		this.ingredients = ingredients;
		
		setFormattedCreationDateTime();
		
	}
	
	//Setter method for creation date time
	private void setFormattedCreationDateTime() {
		
		//Create a DateTime object with local date
		LocalDateTime creationDateTime = LocalDateTime.now();
		
		//Format the date and update instance variable
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.formattedCreationDateTime = creationDateTime.format(dateTimeFormatter);
		
	}

	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeOwner() {
		return recipeOwner;
	}

	public void setRecipeOwner(String recipeOwner) {
		this.recipeOwner = recipeOwner;
	}

	public Boolean getIsVegetarian() {
		return isVegetarian;
	}

	public void setIsVegetarian(Boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

	public String getFormattedCreationDateTime() {
		return formattedCreationDateTime;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	
	
	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", recipeName=" + recipeName + ", recipeOwner=" + recipeOwner
				+ ", isVegetarian=" + isVegetarian + ", formattedCreationDateTime=" + formattedCreationDateTime + ", capacity="
				+ capacity + "]";
	}
	
	//override equals method to enforce field by field comparison when comparing Recipe objects for equality
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		Recipe currentRecipe = (Recipe)this;
		Recipe paramRecipe = (Recipe)obj;
		
		boolean areEqual = false;
		
		if(this == obj)
			return true;
		
		if(obj == null || this.getClass() != obj.getClass())
			return false;
		
		//custom definition when two recipe objects are said to be equal
		if(currentRecipe.getRecipeId().toString().equals(paramRecipe.getRecipeId().toString())
				&& currentRecipe.getRecipeName().equals(paramRecipe.getRecipeName())
				&& currentRecipe.getRecipeOwner().equals(paramRecipe.getRecipeOwner())
				&& currentRecipe.getCapacity() == paramRecipe.getCapacity()
				&& currentRecipe.getIsVegetarian() == paramRecipe.getIsVegetarian()
				&& currentRecipe.getFormattedCreationDateTime().equals(paramRecipe.getFormattedCreationDateTime())
				&& currentRecipe.getIngredients().containsAll(paramRecipe.getIngredients())) {
			
			areEqual = true;
		}
		
		return areEqual;
	}
}
