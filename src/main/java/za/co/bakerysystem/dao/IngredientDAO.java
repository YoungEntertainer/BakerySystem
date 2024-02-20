package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.Ingredient;
import za.co.bakerysystem.model.Product;

public interface IngredientDAO {

    boolean createIngredient(Ingredient ingredient);

    boolean updateIngredient(Ingredient ingredient);

    List<Ingredient> getIngredients();

    List<Ingredient> getIngredientsByKeyWord(String keyWord);

    Ingredient getIngredient(int ingredientID);

    int getIngredientQuantity();

    
    List<Ingredient> getIngredientsInStock();
    
    List<Ingredient> getIngredientsToBeOrdered();

    boolean deleteIngredient(int ingredientID);
}
