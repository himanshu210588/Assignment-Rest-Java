package com.assignments.rest.recipes.recipesapi;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.customeExceptions.RecipeNotFoundException;
import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;
import com.assignments.rest.recipes.recipesapi.repository.RecipesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceTests {
	
    @Mock
    private RecipesRepository recipeRepository;

    @InjectMocks
    private RecipeDaoService recipeService;
    
    /*
     * Series of tests below test the get recipes method of recipe service by sending a different combination of query params
     * findAll method of recipe repository has been mocked to reply a pre-defined set pf recipes
     */
    
    //test get of all recipes with no query param. All defined recipes must be returned
    @Test
    public void testGetAllRecipes() {
       
    	//Prepare two recipe objects
    	Recipe recipe1 = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
    	Recipe recipe2 = new Recipe(1002,"Dosa","Himanshu", true, 4, Arrays.asList("onion","tomato"));
    	
    	//mock findAll method of repository to return the above two recipes. Effectively it means we have only these two recipes in database
        Mockito.when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));
        
        //call the get recipes method on recipe service. Note that we pass all arguments as null as we are testing get all recipes without any filters
        List<Recipe> recipes = recipeService.getFilteredRecipes(null,null,null,null);
        
        //prepare expected recipes from above service call. A successful test will require both the above recipes to be returned
        List<Recipe> expectedRecipes = Arrays.asList(recipe1, recipe2);
        
        //all the recipes should be returned for our test to pass
        Assert.assertEquals(expectedRecipes, recipes);
        
    }
    
    //test get of all recipes with query param isVeg(true/false). All veg/non-veg recipes must be returned
    @Test
    public void testRecipes_ByIsVeg() {
       
    	//Prepare two recipe objects
    	Recipe recipe1 = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
    	Recipe recipe2 = new Recipe(1002,"Dosa","Himanshu", false, 4, Arrays.asList("onion","tomato"));
    	
    	//mock findAll method of repository to return the above two recipes. Effectively it means we have only these two recipes in database
        Mockito.when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));
        
        //call the get recipes method on recipe service. Note that we pass all arguments as null except isVeg as we are testing get all veg recipes
        List<Recipe> filteredRecipes = recipeService.getFilteredRecipes("true",null,null,null);
        
        //prepare expected recipes from above service call. A successful test will require both the above recipes to be returned
        List<Recipe> expectedVegRecipes = Arrays.asList(recipe1);
        
        //all the recipes should be returned for our test to pass
        Assert.assertEquals(expectedVegRecipes, filteredRecipes);
        
    }
    
    //test get of all recipes with query param ingredients(list of strings). All recipes with specified ingredients must be returned
    @Test
    public void testRecipes_ByIngredientsList() {
       
    	//Prepare two recipe objects
    	Recipe recipe1 = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
    	Recipe recipe2 = new Recipe(1002,"Dosa","Himanshu", false, 4, Arrays.asList("onion","olives"));
    	
    	//mock findAll method of repository to return the above two recipes. Effectively it means we have only these two recipes in database
        Mockito.when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));
        
        //call the get recipes method on recipe service. Note that we pass all arguments as null except isVeg as we are testing get all veg recipes
        List<Recipe> filteredRecipes = recipeService.getFilteredRecipes(null,null,null,Arrays.asList("onion","olives"));
        
        //prepare expected recipes from above service call. A successful test will require both the above recipes to be returned
        List<Recipe> expectedVegRecipes = Arrays.asList(recipe2);
        
        //all the recipes should be returned for our test to pass
        Assert.assertEquals(expectedVegRecipes, filteredRecipes);
        
    }
    
    //test get of all recipes with query params isVeg(true/false) and ingredients. All veg/non-veg recipes with specified ingredients must be returned
    @Test
    public void testRecipes_combinations_ByIsVegAndIngredientsList() {
       
    	//Prepare two recipe objects
    	Recipe recipe1 = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
    	Recipe recipe2 = new Recipe(1002,"Dosa","Himanshu", true, 4, Arrays.asList("onion","olives"));
    	Recipe recipe3 = new Recipe(1002,"Dosa","Himanshu", false, 4, Arrays.asList("pineapple","tomato"));
    	
    	//mock findAll method of repository to return the above two recipes. Effectively it means we have only these two recipes in database
        Mockito.when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2,recipe3));
        
        //call the get recipes method on recipe service. Note that we pass all arguments as null except isVeg as we are testing get all veg recipes
        List<Recipe> filteredRecipes = recipeService.getFilteredRecipes("true",null,null,Arrays.asList("onion","olives"));
        
        //prepare expected recipes from above service call. A successful test will require both the above recipes to be returned
        List<Recipe> expectedVegRecipes = Arrays.asList(recipe2);
        
        //all the recipes should be returned for our test to pass
        Assert.assertEquals(expectedVegRecipes, filteredRecipes);
        
    } 
    
    /*this simple test checks if the saveRecipe service method is returning the correct recipe object
    repository.save is not doing much other than creating the recipe in the database, so this test is enough to test save recipe(POST on /recipes)*/
    @Test
    public void testSaveRecipe() {
        Recipe recipe = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));

        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);

        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        Assert.assertEquals(recipe, savedRecipe);
    }
    
    /*
     * Series of tests below test various scenarios for update method of recipe service
     * Repository methods findById and save have been mocked to reply pre-defined set of recipes
     */
    
    @Test
    public void testUpdateRecipe_success() {
    	
    	//existing recipe to be returned by mock service for repository.findById
    	Recipe recipeExisting = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
    	
    	//updated recipe to be returned by mock service for repository.save
    	Recipe recipeUpdated = new Recipe(1001,"Dosa","Himanshu", true, 4, Arrays.asList("onion","tomato"));
    	
    	//mock the repository methods
        Mockito.when(recipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(recipeExisting));
        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipeUpdated);
        
        
        Recipe result = recipeService.updateRecipeById(recipeExisting.getRecipeId(), recipeUpdated);
        
        //equals method for Recipe class is overridden to match all fields when it is called upon a recipe object
        Assert.assertEquals(recipeUpdated, result);
    }
    
    //test that immutable fields should not be changed by the update method
    @Test
    public void testUpdateRecipe_immutableFields() {
    	
    	//existing recipe to be returned by mock service for repository.findById
    	Recipe recipeExisting = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
    	
    	//updated recipe to be returned by mock service for repository.save
    	Recipe recipePassed = new Recipe(1001,"Dosa","Himanshu", true, 4, Arrays.asList("onion","tomato"));
    	
    	Recipe recipeExpected = new Recipe(1001,"Dosa","Monu", true, 4, Arrays.asList("onion","tomato"));
    	
    	//mock the repository methods
        Mockito.when(recipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(recipeExisting));
        
        //output of save method is not really required for this test case
        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipeExpected);
        
        
        Recipe recipeReturnedByService = recipeService.updateRecipeById(recipeExisting.getRecipeId(), recipePassed);
        
        Assert.assertEquals(recipeExpected.getRecipeId(), recipeReturnedByService.getRecipeId());
    }
    
    //test if the update method returns correct exception with correct message when a recipe could not be found to update
    @Test
    public void testUpdateRecipe_exceptions() {
    	
    	
    	//updated recipe to be returned by mock service for repository.save
    	Recipe recipePassed = new Recipe(1001,"Dosa","Himanshu", true, 4, Arrays.asList("onion","tomato"));
    	
    	
    	//mock the repository method findById to return an empty Optional
        Mockito.when(recipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        
        //capture the thrown exception by the update function of recipeService
        Exception exception = assertThrows(RecipeNotFoundException.class, () -> {
        	recipeService.updateRecipeById(recipePassed.getRecipeId(), recipePassed);
        });
        
        //get the actual message from the exception and also prepare the expected message
        String expectedMessage = "Not Found - Recipe Id : "+recipePassed.getRecipeId();
        String actualMessage = exception.getMessage();
        
        //verify whether the exception thrown has the exact same message as the one configured in code
        assertTrue(actualMessage.contains(expectedMessage));
        
    }
    
    //test delete recipe method, just check of the delete method of repository was called
    @Test
    public void testDeleteRecipe() {
    	
    	//recipe object to be deleted
    	Recipe recipeExisting = new Recipe(1001,"Pizza","Monu", true, 4, Arrays.asList("onion","tomato"));
        
    	//since delete method returns void we cannot check on return value, instead we check if the method was called
        Mockito.doNothing().when(recipeRepository).deleteById(anyInt());
        
        //call the delete method of repository service
        recipeService.deleteById(recipeExisting.getRecipeId());
        
        //verify if the delete method of repository was called
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }

}