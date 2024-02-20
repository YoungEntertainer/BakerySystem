package za.co.bakerysystem.service.impl;

import java.util.List;
import za.co.bakerysystem.dao.RecipeIngredientDAO;
import za.co.bakerysystem.dao.impl.RecipeIngredientDAOImpl;
import za.co.bakerysystem.model.Product;
import za.co.bakerysystem.model.RecipeIngredient;
import za.co.bakerysystem.service.RecipeIngredientService;

public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private RecipeIngredientDAO recipeIngredientDAO;

    public RecipeIngredientServiceImpl(RecipeIngredientDAO recipeIngredientDAO) {
        this.recipeIngredientDAO = recipeIngredientDAO;
    }

    @Override
    public boolean createRecipeIngredient(int recipeID, int ingredientID, int grams) {
        return recipeIngredientDAO.createRecipeIngredient(recipeID, ingredientID, grams);
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredients(Product product) {
        return recipeIngredientDAO.getRecipeIngredients(product);
    }

    @Override
    public boolean deleteRecipeIngredient(int recipeID, int ingredientID) {
        return recipeIngredientDAO.deleteRecipeIngredient(recipeID, ingredientID);
    }

    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        RecipeIngredientDAO recipeIngredientDAO = new RecipeIngredientDAOImpl();
        RecipeIngredientService recipeIngredientService = new RecipeIngredientServiceImpl(recipeIngredientDAO);

        // Test createRecipeIngredient method
//        int recipeID = 2;
//        int ingredientID = 4;
//        int grams = 2000;
//
//        boolean createResult = recipeIngredientService.createRecipeIngredient(recipeID, ingredientID, grams);
//
//        if (createResult) {
//            System.out.println("RecipeIngredient created successfully!");
//        } else {
//            System.out.println("Failed to create RecipeIngredient.");
//        }
//        // Test deleteRecipeIngredient method
//        boolean deleteResult = recipeIngredientService.deleteRecipeIngredient(recipeID, ingredientID);
//
//        if (deleteResult) {
//            System.out.println("RecipeIngredient deleted successfully!");
//        } else {
//            System.out.println("Failed to delete RecipeIngredient.");
//        }
    }
}
