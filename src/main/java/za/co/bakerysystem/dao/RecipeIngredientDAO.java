
package za.co.bakerysystem.dao;

public interface RecipeIngredientDAO {

   void createRecipeIngredient(int recipeID, int ingredientID, int grams);

    void deleteRecipeIngredient(int recipeID, int ingredientID);
}

