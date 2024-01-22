package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import za.co.bakerysystem.dao.RecipeDAO;

public class RecipeDAOImpl implements RecipeDAO {

    private static final String JDBC_URL = "your_jdbc_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public void createRecipe(int productID, String comment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Recipe VALUES(?,?)")) {

            statement.setInt(1, productID);
            statement.setString(2, comment);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createRecipeIngredient(int recipeID, int ingredientID, int grams) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Recipe_Ingredient VALUES(?,?,?)")) {

            statement.setInt(1, recipeID);
            statement.setInt(2, ingredientID);
            statement.setInt(3, grams);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecipeDetail(int recipeID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Recipe_Ingredient WHERE Recipe_ID = ?")) {

            statement.setInt(1, recipeID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
