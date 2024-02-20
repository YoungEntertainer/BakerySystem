package za.co.bakerysystem.service.impl;

import java.util.List;
import za.co.bakerysystem.dao.IngredientDAO;
import za.co.bakerysystem.dao.impl.IngredientDAOImpl;
import za.co.bakerysystem.model.Ingredient;
import za.co.bakerysystem.service.IngredientService;

public class IngredientServiceImpl implements IngredientService {

    private IngredientDAO ingredientDAO;

    public IngredientServiceImpl(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    @Override
    public boolean createIngredient(Ingredient ingredient) {
        return ingredientDAO.createIngredient(ingredient);
    }

    @Override
    public List<Ingredient> getIngredientsInStock() {
        return ingredientDAO.getIngredientsInStock();
    }

    @Override
    public List<Ingredient> getIngredientsToBeOrdered() {
        return ingredientDAO.getIngredientsToBeOrdered();
    }

    @Override
    public boolean updateIngredient(Ingredient ingredient) {
        return ingredientDAO.updateIngredient(ingredient);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredientDAO.getIngredients();
    }

    @Override
    public List<Ingredient> getIngredientsByKeyWord(String keyWord) {
        return ingredientDAO.getIngredientsByKeyWord(keyWord);
    }

    @Override
    public Ingredient getIngredient(int ingredientID) {
        return ingredientDAO.getIngredient(ingredientID);
    }

    @Override
    public int getIngredientQuantity() {
        return ingredientDAO.getIngredientQuantity();
    }

   

    @Override
    public boolean deleteIngredient(int ingredientID) {
        return ingredientDAO.deleteIngredient(ingredientID);
    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        IngredientDAO ingredientDAO = new IngredientDAOImpl();
        IngredientService ingredientService = new IngredientServiceImpl(ingredientDAO);

        // Test createIngredient
        //Ingredient newIngredient = new Ingredient("Sugar",50,"brown sugar");
//        boolean created = ingredientService.createIngredient(newIngredient);
//        System.out.println("Ingredient created: " + created);
        // Test updateIngredient
//        Ingredient ngredient = ingredientDAO.getIngredient(1);
//        ngredient.setName("UpdatedName"); // Assuming setName method exists in Ingredient class
//        boolean updated = ingredientService.updateIngredient(ngredient);
//        System.out.println("Ingredient updated: " + updated);
//        // Test getIngredients
//        List<Ingredient> allIngredients = ingredientService.getIngredients();
//        System.out.println("All Ingredients: " + allIngredients);
        // Test getIngredientsByKeyWord
//        String keyword = "sugar";
//        List<Ingredient> ingredientsByKeyword = ingredientService.getIngredientsByKeyWord(keyword);
//        System.out.println("Ingredients with keyword '" + keyword + "': " + ingredientsByKeyword);
//        // Test getIngredient
        int ingredientID = 1; // Replace with an existing ingredient ID
        Ingredient retrievedIngredient = ingredientService.getIngredient(ingredientID);
        System.out.println("Ingredient with ID " + ingredientID + ": " + retrievedIngredient);
//        // Test getIngredientQuantity
//        int ingredientQuantity = ingredientService.getIngredientQuantity();
//        System.out.println("Total ingredient quantity: " + ingredientQuantity);
        // Test getRelatedProducts
//        int ingredientIdForProducts = 2; // Replace with an existing ingredient ID
//        List<Product> relatedProducts = ingredientService.getRelatedProducts(ingredientIdForProducts);
//        System.out.println("Related Products for Ingredient ID " + ingredientIdForProducts + ": " + relatedProducts);

//        // Test deleteIngredient
//        int ingredientIdToDelete = 1; // Replace with an existing ingredient ID
//        boolean deleted = ingredientService.deleteIngredient(ingredientIdToDelete);
//        System.out.println("Ingredient deleted: " + deleted);

   // Test Ingredients In Stock
//        System.out.println("Report: Ingredients In Stock");
//        List<Ingredient> ingredientsInStock = ingredientService.getIngredientsInStock();
//        System.out.println(ingredientsInStock);

        // Test Ingredients Required to be Ordered
//        System.out.println("Report: Ingredients Required to be Ordered");
//        List<Ingredient> ingredientsToBeOrdered = ingredientService.getIngredientsToBeOrdered();
//        System.out.println(ingredientsToBeOrdered);
    }
}
