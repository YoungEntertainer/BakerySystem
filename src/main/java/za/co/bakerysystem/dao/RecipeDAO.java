package za.co.bakerysystem.dao;

public interface RecipeDAO {

    void createRecipe(int productID, String comment);

    void createRecipeIngredient(int recipeID, int ingredientID, int grams);

    void deleteRecipeDetail(int recipeID);
}
