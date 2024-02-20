package za.co.bakerysystem.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dao.ShoppingCartDAO;
import za.co.bakerysystem.dao.impl.ProductDAOImpl;
import za.co.bakerysystem.dao.impl.ShoppingCartDAOImpl;
import static za.co.bakerysystem.dao.impl.ShoppingCartDAOImpl.db;
import za.co.bakerysystem.model.Product;
import za.co.bakerysystem.model.RecipeIngredient;
import za.co.bakerysystem.model.ShoppingCart;
import za.co.bakerysystem.service.ShoppingCartService;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartDAO shoppingCartDAO;
    private ProductDAO productDAO;
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final String CHECK_INGREDIENT_STOCK = "SELECT quantity FROM ingredient WHERE id = ?";
    private static final String GET_RECIPE_INGREDIENTS = "SELECT * FROM recipe_ingredient WHERE recipe_id = ?";

    public ShoppingCartServiceImpl(ShoppingCartDAO shoppingCartDAO, ProductDAO productDAO) {
        this.shoppingCartDAO = shoppingCartDAO;
        this.productDAO = productDAO;
    }

    @Override
    public ShoppingCart getShoppingCartById(int cartID) {
        return shoppingCartDAO.getShoppingCartById(cartID);
    }
//---------------------------------------------------------------------------------------------------

    @Override
    public boolean addProductToCart(int cartID, Product product, int quantity) {
        if (product != null && canMakeProduct(product, quantity)) {
            // If canMakeProduct returns true, proceed to add the product to the cart
            return shoppingCartDAO.addProductToCart(cartID, product, quantity);
        }
        return false;
    }

    // Helper method to check if the product can be made with the available ingredients
    private boolean canMakeProduct(Product product, int quantity) {
        List<RecipeIngredient> recipeIngredients = getRecipeIngredients(product);

        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            int requiredQuantity = recipeIngredient.getQuantity() * quantity;
            if (!hasEnoughIngredientStock(recipeIngredient.getIngredientID(), requiredQuantity)) {
                // Display an error message or handle out-of-stock scenario
                System.out.println("Product is out of stock. Ingredient ID: " + recipeIngredient.getIngredientID());
                return false;
            }
        }

        return true;
    }

    // Helper method to retrieve the list of ingredients in the product's recipe
    private List<RecipeIngredient> getRecipeIngredients(Product product) {

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(GET_RECIPE_INGREDIENTS);
            ps.setInt(1, product.getID());
            // Execute the query to get recipe ingredients
            rs = ps.executeQuery();

            List<RecipeIngredient> recipeIngredients = new ArrayList<>();

            while (rs.next()) {
                int ingredientID = rs.getInt("ingredient_id");
                int quantity = rs.getInt("quantity");
                RecipeIngredient recipeIngredient = new RecipeIngredient(product.getID(), ingredientID, quantity);
                recipeIngredients.add(recipeIngredient);
            }

            return recipeIngredients;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return null;
        }

    }

    // Helper method to check if there is enough stock for a specific ingredient
    private boolean hasEnoughIngredientStock(int ingredientID, int requiredQuantity) {

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(CHECK_INGREDIENT_STOCK);
            ps.setInt(1, ingredientID);
            // Execute the query to check ingredient stock
            rs = ps.executeQuery();

            // Placeholder logic to check if there is enough stock, replace with actual logic
            if (rs.next()) {
                int availableQuantity = rs.getInt("quantity");
                return requiredQuantity <= availableQuantity;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

//---------------------------------------------------------------------------------------------------
    @Override
    public boolean removeProductFromCart(int cartID, Product product) {
        if (product != null) {
            return shoppingCartDAO.removeProductFromCart(cartID, product);
        }
        return false;
    }

    @Override
    public double calculateTotalAmount(int cartID) {
        return shoppingCartDAO.calculateTotalAmount(cartID);
    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Instantiate necessary DAO and Service objects
        ProductDAO productDAO = new ProductDAOImpl();
        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
        ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDAO, productDAO);

//        // Test getShoppingCartById
//        int sampleCartID = 1;  // Replace with an actual cart ID for testing
//        ShoppingCart cart = shoppingCartService.getShoppingCartById(sampleCartID);
//        System.out.println("Shopping Cart for ID " + sampleCartID + ": " + cart);
//        // Test addProductToCart
//        // Create a new product and add it to the cart
//        Product newProduct = new Product("Blue", 4.99, 6.7, 2, "GOOD BREAD", "HIGH IN carbo", "fibre", "none", 2);
//        productDAO.createProduct(newProduct);
        // Get the newly added product from the database
        Product addedProduct = productDAO.getProductsByKeyWord("Bread").get(0);

        // Add the product to the cart
        boolean addProductResult = shoppingCartService.addProductToCart(2, addedProduct, 3);
        System.out.println("Add Product to Cart Result: " + addProductResult);

//        // Test removeProductFromCart
//        // Remove the product from the cart
//        boolean removeProductResult = shoppingCartService.removeProductFromCart(sampleCartID, addedProduct);
//        System.out.println("Remove Product from Cart Result: " + removeProductResult);
//
//        // Test updateCartTotal
//        // Update the total for the cart
//        boolean updateTotalResult = shoppingCartService.updateCartTotal(sampleCartID);
//        System.out.println("Update Cart Total Result: " + updateTotalResult);
    }

}
