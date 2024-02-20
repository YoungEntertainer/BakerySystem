package za.co.bakerysystem.service;

import java.util.List;
import za.co.bakerysystem.exception.recipe.DuplicateRecipeException;
import za.co.bakerysystem.model.Recipe;

public interface RecipeService {

    boolean createRecipe(int productID, String comment);

    List<String> getRecipe(int productID);

    boolean deleteRecipeDetail(int recipeID);

    List<Recipe> getRecipes();

    boolean exists(int productID) throws DuplicateRecipeException;
}
