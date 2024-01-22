
package za.co.bakerysystem.model;

public class RecipeIngredient {

    private int recipeID;
    private int ingredientID;
    private Integer grams;

    public RecipeIngredient(int recipeID, int ingredientID, Integer grams) {
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
        this.grams = grams;
    }

    public RecipeIngredient() {
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" + "recipeID=" + recipeID + ", ingredientID=" + ingredientID + ", grams=" + grams + '}';
    }

    
}
