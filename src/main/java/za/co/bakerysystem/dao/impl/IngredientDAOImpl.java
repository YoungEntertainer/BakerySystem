package za.co.bakerysystem.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.IngredientDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Ingredient;
import za.co.bakerysystem.model.Product;

public class IngredientDAOImpl implements IngredientDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;

    public IngredientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public IngredientDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public boolean createIngredient(Ingredient ingredient) {

        boolean retVal = false;
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("INSERT INTO Ingredient (Name, PricePerKG, Note,Quantity,unitid) VALUES (?,?, ?, ?,?)");
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getPricePerKG());
            ps.setString(3, ingredient.getNote());
            ps.setInt(4, ingredient.getQuantity());
            ps.setInt(5, ingredient.getUnitID());

            retVal = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        return retVal;
    }

    @Override
    public boolean updateIngredient(Ingredient ingredient) {
        connection = db.getConnection();
        boolean retVal = false;
        try {

            ps = connection.prepareStatement("UPDATE Ingredient SET Name=?, PricePerKG=?, Note=?,quantity=?,unitid=? WHERE ID=?");
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getPricePerKG());
            ps.setString(3, ingredient.getNote());
            ps.setInt(4, ingredient.getQuantity());
            ps.setInt(5, ingredient.getUnitID());
            ps.setInt(6, ingredient.getID());

            retVal = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }
        return retVal;

    }

    @Override
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        connection = db.getConnection();

        try {

            ps = connection.prepareStatement("SELECT * FROM Ingredient");
            rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient ingredient = extractIngredientFromResultSet(rs);
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByKeyWord(String keyWord) {
        List<Ingredient> ingredients = new ArrayList<>();
        connection = db.getConnection();

        try {

            ps = connection.prepareCall("CALL fetch_ingredients_keyword(?)");
            ps.setString(1, keyWord);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = extractIngredientFromResultSet(rs);
                    ingredients.add(ingredient);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return ingredients;
    }

    @Override
    public Ingredient getIngredient(int ingredientID) {
        Ingredient ingredient = null;
        connection = db.getConnection();

        try {

            ps = connection.prepareCall("CALL fetch_ingredient_info(?)");

            ps.setInt(1, ingredientID);
            rs = ps.executeQuery();
            if (rs.next()) {
                ingredient = extractIngredientFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return ingredient;
    }

    @Override
    public int getIngredientQuantity() {
        int quantity = 0;
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("SELECT COUNT(ID) as quantity FROM Ingredient");
            rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return quantity;
    }

// Report 4: Ingredients in Stock
    @Override
    public List<Ingredient> getIngredientsInStock() {
        List<Ingredient> ingredientsInStock = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "SELECT * FROM Ingredient ORDER BY Name";
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("name");
                double pricePerKG = rs.getDouble("pricePerKG");
                String note = rs.getString("note");
                int quantity = rs.getInt("quantity");
                int unitid = rs.getInt("unitid");
                Ingredient ingredient = new Ingredient(ID, name, pricePerKG, note, quantity, unitid);
                ingredientsInStock.add(ingredient);
            }
        } catch (SQLException e) {
            System.out.println("Error getting ingredients in stock: " + e.getMessage());
        }
        return ingredientsInStock;
    }

    // Report 5: Ingredients Required to be Ordered
    @Override
    public List<Ingredient> getIngredientsToBeOrdered() {
        List<Ingredient> ingredientsToBeOrdered = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "SELECT * FROM Ingredient WHERE quantity < 5000 ORDER BY Name";
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("name");
                double pricePerKG = rs.getDouble("pricePerKG");
                String note = rs.getString("note");
                int quantity = rs.getInt("quantity");
                int unitid = rs.getInt("unitid");

                Ingredient ingredient = new Ingredient(ID, name, pricePerKG, note, quantity, unitid);
                ingredientsToBeOrdered.add(ingredient);
            }
        } catch (SQLException e) {
            System.out.println("Error getting ingredients to be ordered: " + e.getMessage());
        }
        return ingredientsToBeOrdered;
    }

    @Override
    public boolean deleteIngredient(int ingredientID) {
        connection = db.getConnection();
        boolean retVal = false;

        try {

            ps = connection.prepareStatement("DELETE FROM Ingredient WHERE ID = ?");
            ps.setInt(1, ingredientID);
            retVal = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return retVal;
    }

    private Ingredient extractIngredientFromResultSet(ResultSet rs) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setID(rs.getInt("ID"));
        ingredient.setName(rs.getString("Name"));
        ingredient.setPricePerKG(rs.getDouble("PricePerKG"));
        ingredient.setNote(rs.getString("Note"));
        ingredient.setQuantity(rs.getInt("quantity"));
        ingredient.setUnitID(rs.getInt("unitid"));
        return ingredient;
    }

    private Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setID(rs.getInt("ID"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("total")); //it is the total number of times the price was bought

        return product;

    }

    public static void main(String[] args) {
        Ingredient ingredient = new Ingredient("Flour", 97.0, "Raising flour for baking cakes", 12, 1);
//        Ingredient ingredient1 = new Ingredient(1, "Baking powder", 17.0, "Baking powder for cake");
//
        IngredientDAO ingredientDAO = new IngredientDAOImpl();
//
//        //test for add ingredient
//        if (ingredientDAO.createIngredient(ingredient)) {
//            System.out.println("Success");
//
//        } else {
//            System.out.println("Failed");
//
//        }
        //test for update ingredient
//                if (ingredientDAO.updateIngredient(ingredient1)) {
//                    System.out.println("Successfully updated ");
//        
//                } else {
//                    System.out.println("Failed");
//        
//                }
        //Test for getIngredient 
        System.out.println(ingredientDAO.getIngredient(1));
        //Test for getIngredientsByKeyWord
//        List<Ingredient> listOfIngredientByKeyWord = ingredientDAO.getIngredientsByKeyWord("powder");
//
//        listOfIngredientByKeyWord.forEach(product1 -> {
//            System.out.println(product1);
//        });
        //Test for getIngredients
//         List<Ingredient> listOfIngredient = ingredientDAO.getIngredients();
//
//        listOfIngredient.forEach(product1 -> {
//            System.out.println(product1);
//        });
        //Test for getIngredientQuantity() 
        // System.out.println("Number of ingredients are: " + ingredientDAO.getIngredientQuantity());

    }
}
