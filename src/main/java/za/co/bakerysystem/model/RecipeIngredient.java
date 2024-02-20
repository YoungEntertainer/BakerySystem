
package za.co.bakerysystem.model;

public class RecipeIngredient {

    private int recipeID;
    private int ingredientID;
    private int quantity;

    public RecipeIngredient(int recipeID, int ingredientID, int quantity) {
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" + "recipeID=" + recipeID + ", ingredientID=" + ingredientID + ", quantity=" + quantity + '}';
    }

    
}
