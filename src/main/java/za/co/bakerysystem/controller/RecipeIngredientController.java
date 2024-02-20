package za.co.bakerysystem.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.RecipeIngredientDAO;
import za.co.bakerysystem.dao.impl.RecipeIngredientDAOImpl;
import za.co.bakerysystem.model.RecipeIngredient;
import za.co.bakerysystem.service.RecipeIngredientService;
import za.co.bakerysystem.service.impl.RecipeIngredientServiceImpl;

@Path("/recipe_ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientDAO recipeIngredientDAO = new RecipeIngredientDAOImpl();
    private final RecipeIngredientService recipeIngredientService = new RecipeIngredientServiceImpl(recipeIngredientDAO);

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipeIngredient(RecipeIngredient recipeIngredient) {
        int recipeID = recipeIngredient.getRecipeID();
        int ingredientID = recipeIngredient.getIngredientID();
        int quantity = recipeIngredient.getQuantity();

        if (recipeIngredientService.createRecipeIngredient(recipeID, ingredientID, quantity)) {
            return Response.status(Response.Status.CREATED).entity("Recipe ingredient created successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create recipe ingredient").build();
        }
    }

    @DELETE
    @Path("/delete")
    public Response deleteRecipeIngredient(
            @QueryParam("recipeID") int recipeID,
            @QueryParam("ingredientID") int ingredientID) {
        if (recipeIngredientService.deleteRecipeIngredient(recipeID, ingredientID)) {
            return Response.ok("Recipe ingredient deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Recipe ingredient not found").build();
        }
    }
}
