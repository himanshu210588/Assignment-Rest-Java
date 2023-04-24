package com.assignments.rest.recipes.recipesapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assignments.rest.recipes.recipesapi.beans.Recipe;
import com.assignments.rest.recipes.recipesapi.controllers.RecipeController;
import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTestsPostCsrf {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeDaoService recipeService;
    
    private static final String username = "admin";
    private static final String password = "admin";
    private static HttpHeaders headers;
    /*
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }*/
    
    //this method is executed only once for all tests in this class and hence is made static
    @BeforeAll
    public static void prepareHeaders() {
    	
    	//prepare headers for basic authentication
    	String authHeader = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(authHeader.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authHeaderValue);
    	
    }
    
    @Test
    public void testCreateRecipe_success() throws Exception {
    	
    	
        //object to be returned by mock service
    	Recipe recipe1 = new Recipe(101,"Pasta Carbonara", "John", true, 4, Arrays.asList("onion","tomato"));

        //setup mock service
        when(recipeService.saveRecipe(any())).thenReturn(recipe1);
        
        mockMvc.perform(post("/recipes")
        		.headers(headers)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                .content("{\"recipeName\": \"Dosa\", \"recipeOwner\": \"Monu\", \"isVegetarian\": true, \"capacity\": 4}]}")
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isCreated());

    }
    
    @Test
    public void testCreateRecipe_invalidRecipeName() throws Exception {
    	
        //object to be returned by mock service
    	Recipe recipe1 = new Recipe(101,"Pasta Carbonara", "John", true, 4, Arrays.asList("onion","tomato"));

        //setup mock service
        when(recipeService.saveRecipe(any())).thenReturn(recipe1);
        
        mockMvc.perform(post("/recipes")
        		.headers(headers)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                .content("{\"recipeName\": \"Butter Paneer Ghee Roasted Masala Dosa\", \"recipeOwner\": \"Monu\", \"isVegetarian\": true, \"capacity\": 4}]}")
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isBadRequest());

    }
    
    @Test
    public void testCreateRecipe_invalidOwnerName() throws Exception {
    	
        //object to be returned by mock service
    	Recipe recipe1 = new Recipe(101,"Pasta Carbonara", "John", true, 4, Arrays.asList("onion","tomato"));

        //setup mock service
        when(recipeService.saveRecipe(any())).thenReturn(recipe1);
        
        mockMvc.perform(post("/recipes")
        		.headers(headers)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                .content("{\"recipeName\": \"Butter Paneer \", \"recipeOwner\": \"Gangadhar Vidyadhar Omkarnath Shastri\", \"isVegetarian\": true, \"capacity\": 4}]}")
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isBadRequest());

    }
}
