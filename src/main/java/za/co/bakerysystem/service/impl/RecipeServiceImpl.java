package za.co.bakerysystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.bakerysystem.dao.RecipeDAO;
import za.co.bakerysystem.dao.impl.RecipeDAOImpl;
import za.co.bakerysystem.exception.recipe.DuplicateRecipeException;
import za.co.bakerysystem.model.Recipe;
import za.co.bakerysystem.service.RecipeService;

public class RecipeServiceImpl implements RecipeService {

    private RecipeDAO recipeDAO;

    public RecipeServiceImpl(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    public RecipeServiceImpl() {
        this.recipeDAO = new RecipeDAOImpl();
    }

    @Override
    public boolean createRecipe(int productID, String comment) {
        return recipeDAO.createRecipe(productID, comment);
    }

    @Override
    public List<String> getRecipe(int productID) {
        return recipeDAO.getRecipe(productID);
    }

    @Override
    public boolean deleteRecipeDetail(int recipeID) {
        return recipeDAO.deleteRecipeDetail(recipeID);
    }

    @Override
    public List<Recipe> getRecipes() {
        return recipeDAO.getRecipes();
    }

    @Override
    public boolean exists(int productID) throws DuplicateRecipeException {
        if (recipeDAO.getRecipes().stream().anyMatch(recipe -> recipe.getProductID() == productID)) {
            throw new DuplicateRecipeException("Recipe already exist");
        }
        return false;

    }

    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Create an instance of RecipeServiceImpl
        RecipeService recipeService = new RecipeServiceImpl();
//
//        // Test createRecipe method
//        int productID = 5;
//        String comment = "Delicious recipe!";
//
//        boolean createResult = recipeService.createRecipe(productID, comment);
//
//        if (createResult) {
//            System.out.println("Recipe created successfully!");
//        } else {
//            System.out.println("Failed to create Recipe.");
//        }
        try {
            //        // Test deleteRecipeDetail method
//        int recipeIDToDelete = 1;
//
//        boolean deleteResult = recipeService.deleteRecipeDetail(recipeIDToDelete);
//
//        if (deleteResult) {
//            System.out.println("Recipe detail deleted successfully!");
//        } else {
//            System.out.println("Failed to delete Recipe detail.");
//        }
//Test Exist
            System.out.println(recipeService.exists(2));
        } catch (DuplicateRecipeException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
