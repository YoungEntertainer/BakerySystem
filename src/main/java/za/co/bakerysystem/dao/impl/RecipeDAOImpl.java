package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.RecipeDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Recipe;

public class RecipeDAOImpl implements RecipeDAO {

    private Connection connection;
    private PreparedStatement ps;
    private static DbManager db;
    private ResultSet rs;

    public RecipeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public RecipeDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public List<String> getRecipe(int productID) {
        List<String> recipe = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_product_recipe(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            while (rs.next()) {
                recipe.add(rs.getString("recipe"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return recipe;
    }

    @Override
    public boolean createRecipe(int productID, String comment) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("INSERT INTO Recipe VALUES(?,?)");

            ps.setInt(1, productID);
            ps.setString(2, comment);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }
        return false;
    }

    @Override
    public boolean deleteRecipeDetail(int recipeID) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("DELETE FROM Recipe WHERE product_id = ?");

            ps.setInt(1, recipeID);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        db = DbManager.getInstance();

        connection = db.getConnection();

        try {

            ps = connection.prepareStatement("SELECT * FROM Recipe");

            rs = ps.executeQuery();

            while (rs.next()) {
                Recipe recipe = extractRecipeFromResultSet(rs);
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return recipes;
    }

    private Recipe extractRecipeFromResultSet(ResultSet rs) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setProductID(rs.getInt("product_id"));
        recipe.setComment(rs.getString("comment"));

        return recipe;
    }

    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        RecipeDAO recipeDAO = new RecipeDAOImpl();

        // Test createRecipe
//        boolean createRecipeSuccess = recipeDAO.createRecipe(1, "Test Recipe"); // make sure productid exists
//        System.out.println("Create Recipe success: " + createRecipeSuccess);
        // Test createRecipeRecipe
//        boolean createRecipeRecipeSuccess = recipeDAO.createRecipeRecipe(2, 2, 100);
//        System.out.println("Create Recipe Recipe success: " + createRecipeRecipeSuccess);
//        // Test deleteRecipeDetail
//        boolean deleteRecipeDetailSuccess = recipeDAO.deleteRecipeDetail(15);
//        System.out.println("Delete Recipe Detail success: " + deleteRecipeDetailSuccess);
        //Test get All Recipe
        List<Recipe> recipes = recipeDAO.getRecipes();

        for (Recipe recipe : recipes) {
            System.out.println(recipes);
        }

    }

}
