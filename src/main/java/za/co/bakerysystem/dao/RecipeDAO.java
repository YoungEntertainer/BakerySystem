package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.Recipe;

public interface RecipeDAO {

    boolean createRecipe(int productID, String comment);

    List<String> getRecipe(int productID);

    boolean deleteRecipeDetail(int recipeID);

    List<Recipe> getRecipes();
}
