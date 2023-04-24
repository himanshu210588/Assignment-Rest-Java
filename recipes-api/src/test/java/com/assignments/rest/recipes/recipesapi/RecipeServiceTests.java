package com.assignments.rest.recipes.recipesapi;

import java.util.Arrays;
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
import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;
import com.assignments.rest.recipes.recipesapi.repository.RecipesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceTests {
	
    @Mock
    private RecipesRepository recipeRepository;

    @InjectMocks
    private RecipeDaoService recipeService;
    
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
    */
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
        
        Assert.assertEquals(recipeExpected.getRecipeOwner(), recipeReturnedByService.getRecipeOwner());
    }
/*

    @Test
    public void testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));

        recipeService.deleteRecipe(recipe.getId());

        Mockito.verify(recipeRepository, Mockito.times(1)).delete(Mockito.any(Recipe.class));
    }

    @Test
    public void testGetAllRecipes() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Pizza");
        recipe1.setCapacity(4);
        recipe1.setIsVeg(true);
        recipe1.setCreationTime(LocalDateTime.now());
        recipe1.setIngredientsList(Arrays.asList("Flour", "Tomatoes", "Cheese"));

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Pasta");
        recipe2.setCapacity(2);
        recipe2.setIsVeg(true);
        recipe2.setCreationTime(LocalDateTime.now());
        recipe2.setIngredientsList(Arrays.asList("Pasta", "Tomatoes", "Cheese"));

        Mockito.when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> recipes = recipeService.getAllRecipes();

        Assert.assertEquals(Arrays.asList(recipe1, recipe2), recipes);
    }

    @Test
    public void testGetRecipesByQueryParams() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Pizza");
        recipe1.setCapacity(4);
        recipe1.setIsVeg(true);
        recipe1.setCreationTime(LocalDateTime.now());
        recipe1.setIngredientsList(Arrays.asList("Flour", "Tomatoes", "Cheese"));

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Pasta");
        recipe2.setCapacity(2);
        recipe2.setIsVeg(true);
        recipe2.setCreationTime(LocalDateTime.now());
        recipe2.setIngredientsList(Arrays.asList("Pasta", "Tomatoes", "Cheese"));

        List<Recipe> expectedRecipes = Arrays.asList(recipe1);

        Mockito.when(recipeRepository.findRecipesByQueryParams(Mockito.anyInt(), Mockito.anyBoolean(), Mockito.any(LocalDateTime.class), Mockito.anyList())).thenReturn(expectedRecipes);

        List<Recipe> result = recipeService.getRecipesByQueryParams(4, true, LocalDateTime.now(), Arrays.asList("Flour", "Tomatoes", "Cheese"));

        Assert.assertEquals(expectedRecipes, result);
    }
*/

}