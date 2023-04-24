package com.assignments.rest.recipes.recipesapi;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeControllerTestsPostWithJWT {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080"; // Replace with your API endpoint URL
    private final String username = "admin";
    private final String password = "admin";

    @Test
    public void saveRecipe_TestReturnCode() {
    	
        //prepare basic auth header
        String authHeader = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(authHeader.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authHeaderValue);

        //invoke POST request to /authenticate endpoint to get JWT token
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/authenticate",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class);

        //verify if the success code is received
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //capture jwt from the response
        String responseBody = response.getBody();
        String jwtToken = responseBody.substring(responseBody.indexOf(":") + 2, responseBody.length() - 2);
        

        //JWT auth needs to be used for POST requests. So, remove the basic auth and add JWT auth header 
        headers.remove("Authorization");
        headers.setBearerAuth(jwtToken);

        //set up the request body for the second HTTP request
        String requestBody = "{\"recipeName\": \"Pizza\", \"recipeOwner\": \"Monu\"}";

        //POST request to /recipes endpoint to create a recipe
        ResponseEntity<String> response2 = restTemplate.exchange(
                baseUrl + "/recipes",
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class);

        //verify that above response has 201 code after post request
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //String responseBody2 = response2.getBody();
    }
}