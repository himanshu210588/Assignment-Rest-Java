package com.assignments.rest.recipes.recipesapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.controllers.RecipeController;
import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTestsGet {
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeDaoService recipeService;

    @Test
    public void testGetRecipes() throws Exception {

    	//prepare recipes to be returned by the mock service
        Recipe recipe1 = new Recipe(101,"Dosa", "Himanshu", true, 4, Arrays.asList("onion","tomato"));
        Recipe recipe2 = new Recipe(102,"Pizza", "Monu", true, 6, Arrays.asList("olives","cheese"));
      
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        //prepare the auth header for basic authentication
        String authHeader = "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes());

        // mock get service for any combination of parameters
        when(recipeService.getFilteredRecipes(any(),any(),any(),any())).thenReturn(recipes);
        
        //below mock statement can be used for different combinations of parameters
        //when(recipeService.getFilteredRecipes(ArgumentMatchers.anyString(), ArgumentMatchers.isNull(), ArgumentMatchers.anyString(),ArgumentMatchers.isNull())).thenReturn(recipes);


        //mock the GET request and perform assertions
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes")
                .header("Authorization", authHeader)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipeName").value("Dosa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].recipeName").value("Pizza"))
                .andDo(MockMvcResultHandlers.print());
    }
}

