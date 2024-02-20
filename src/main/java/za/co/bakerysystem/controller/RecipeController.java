package za.co.bakerysystem.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.RecipeDAO;
import za.co.bakerysystem.dao.impl.RecipeDAOImpl;
import za.co.bakerysystem.exception.recipe.DuplicateRecipeException;
import za.co.bakerysystem.model.Recipe;
import za.co.bakerysystem.service.RecipeService;
import za.co.bakerysystem.service.impl.RecipeServiceImpl;

@Path("/recipes")
public class RecipeController {

    private final RecipeDAO recipeDAO = new RecipeDAOImpl();
    private final RecipeService recipeService = new RecipeServiceImpl(recipeDAO);

    @POST
    @Path("/create_recipe")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipe(Recipe recipe) {
        int productID = recipe.getProductID();
        String comment = recipe.getComment();

        try {
            recipeService.exists(productID);

            if (recipeService.createRecipe(productID, comment)) {
                return Response.status(Response.Status.CREATED).entity("Recipe created successfully").build();
            }

        } catch (DuplicateRecipeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create recipe").build();

    }

    @DELETE
    @Path("/{recipeID}/delete-detail")
    public Response deleteRecipeDetail(@PathParam("recipeID") int recipeID) {
        if (recipeService.deleteRecipeDetail(recipeID)) {
            return Response.ok("Recipe detail deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Recipe detail not found").build();
        }
    }
}
