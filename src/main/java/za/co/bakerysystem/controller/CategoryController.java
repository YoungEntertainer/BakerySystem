package za.co.bakerysystem.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.CategoryDAO;
import za.co.bakerysystem.dao.impl.CategoryDAOImpl;
import za.co.bakerysystem.exception.category.DuplicateCategoryExcpetion;
import za.co.bakerysystem.model.Category;
import za.co.bakerysystem.service.CategoryService;
import za.co.bakerysystem.service.impl.CategoryServiceImpl;

@Path("/categories")
public class CategoryController {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final CategoryService categoryService = new CategoryServiceImpl(categoryDAO);

    @POST
    @Path("/add_category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(Category category) {
        String message = "";
        try {
            categoryService.exists(category.getDescription().toLowerCase());

            if (categoryDAO.addCategory(category)) {
                message = "Category added successfully";
                return Response.status(Response.Status.CREATED).entity(message).build();
            }

        } catch (DuplicateCategoryExcpetion ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/get_category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(@PathParam("categoryId") int categoryId) {
        if (categoryService.getCategoryById(categoryId) != null) {
            return Response.ok(categoryService.getCategoryById(categoryId)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
        }
    }

    @DELETE
    @Path("/delete_category/{categoryId}")
    public Response deleteCustomer(@PathParam("categoryId") int categoryId) {
        if (categoryService.deleteCategory(categoryId)) {
            return Response.ok("Category deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
        }
    }

    @GET
    @Path("/all_categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategory();

        if (allCategories != null && !allCategories.isEmpty()) {
            return Response.ok(allCategories).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No category found").build();
        }
    }

    @PUT
    @Path("/update_category/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(Category updatedCategory, @PathParam("categoryId") int categoryId) {
        if (categoryService.updateCategory(updatedCategory, categoryId)) {
            return Response.ok("Category updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
        }
    }

}
