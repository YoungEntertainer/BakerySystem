package za.co.bakerysystem.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.IngredientDAO;
import za.co.bakerysystem.dao.impl.IngredientDAOImpl;
import za.co.bakerysystem.model.Ingredient;
import za.co.bakerysystem.service.IngredientService;
import za.co.bakerysystem.service.impl.IngredientServiceImpl;

@Path("/ingredients")
public class IngredientController {

    private final IngredientDAO ingredientDAO = new IngredientDAOImpl();
    private final IngredientService ingredientService = new IngredientServiceImpl(ingredientDAO);

    @POST
    @Path("/add_ingredient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addIngredient(Ingredient ingredient) {
        if (ingredientService.createIngredient(ingredient)) {
            return Response.status(Response.Status.CREATED).entity("Ingredient added successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Ingredient was not added successful!").build();

        }
    }

    @GET
    @Path("/get_ingredient/{ingredientID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientByID(@PathParam("ingredientID") int ingredientID) {
        Ingredient ingredient = ingredientService.getIngredient(ingredientID);

        if (ingredient != null) {
            return Response.ok(ingredientService.getIngredient(ingredientID)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Ingredient not found").build();
        }
    }

    @PUT
    @Path("/update/{ingredientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateIngredient(Ingredient updatedIngredient) {
        if (ingredientService.updateIngredient(updatedIngredient)) {
            return Response.ok("Ingredient updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Ingredient not found").build();
        }
    }

    @DELETE
    @Path("/delete_ingredient/{ingredientId}")
    public Response deleteIngredient(@PathParam("ingredientId") int ingredientId) {
        if (ingredientService.deleteIngredient(ingredientId)) {
            return Response.ok("Ingredient deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Ingredient not found").build();
        }
    }

    @GET
    @Path("/all_ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllIngredients() {
        List<Ingredient> allIngredients = ingredientService.getIngredients();

        if (allIngredients != null && !allIngredients.isEmpty()) {
            return Response.ok(allIngredients).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No ingredients found").build();
        }
    }

    @GET
    @Path("/total_ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientQuantity() {
        int count = ingredientService.getIngredientQuantity();

        if (count > 0) {
            return Response.ok(count).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No ingredients found").build();
        }
    }

    @GET
    @Path("/ingredients_to_be_ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientsToBeOrdered() {
        List<Ingredient> allIngredients = ingredientService.getIngredientsToBeOrdered();

        if (allIngredients != null && !allIngredients.isEmpty()) {
            return Response.ok(allIngredients).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No ingredients found").build();
        }
    }

    @GET
    @Path("/ingredients_in_stock")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientsInStock() {
        List<Ingredient> allIngredients = ingredientService.getIngredientsInStock();

        if (allIngredients != null && !allIngredients.isEmpty()) {
            return Response.ok(allIngredients).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No ingredients found").build();
        }
    }

}
