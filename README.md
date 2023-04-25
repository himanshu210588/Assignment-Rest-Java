# Assignment-Rest-Java
Description:
This project creates a set of api for managing recipes. You can create,delete,update your favourite recipes as well as get available recipes.
This project uses Java SpringBoot framework to build the api and JPA/Hibernate for data persistence. H2 database has been used.

Details :

The swagger definition and open api yaml documentation is available below :
http://localhost:8080/swagger-ui
http://localhost:8080/v3/api-docs

The REST api consists of following operations :

GET(/recipes)
- This endpoint retrieves all the available recipes
- Following optional query parameters may be passed in the query string either standalone or in any combination. Filtered recipes should be returned
  - isVeg(Boolean)
  - capacity(min: 1, max:10)
  - creationDate(string format - dd-MM-yyyy HH:mm)
  - ingredients(List<String>)
  
POST(/recipes)
- This endpoint recieves a POST request to create a new recipe with a generated recipeId
- Requires a json body(see below example json)
  {
"recipeName": "Dosa",
"recipeOwner": "Himanshu",
"isVegetarian": true,
"capacity": 4,
"ingredients":[
"tomato",
"onion"
]
}
- Requires a JWT authentication
  - JWT can be obtained by sending a POST request to /authenticate with Basic Auth(user - admin, pass - admin)
  
PUT(/recipes/{recipeId})
 - This endpoint receives a PUT request to update an existing recipe
 - Requires a JSON body(see above in POST description for example json body)
 - Requires a JWT authentication
   - JWT can be obtained by sending a POST request to /authenticate with Basic Auth(user - admin, pass - admin)
 
 DELETE(/recipes/{recipeId})
 - This endpoint receieves a DELETE request to delete an existing recipe
