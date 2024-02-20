package za.co.bakerysystem.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Product;

public class ProductDAOImpl implements ProductDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;
    private CallableStatement cs;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public ProductDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public List<Product> getRelatedProducts(int ingredientID) {
        List<Product> ingredients = new ArrayList<>();

        connection = DbManager.getInstance().getConnection();

        try {
            CallableStatement cs = connection.prepareCall("CALL fetch_ingredient_products(?)");
            cs.setInt(1, ingredientID);
            rs = cs.executeQuery();

            while (rs.next()) {
                Product ingredient = extractProductFromResultSet(rs);
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());

        }

        return ingredients;
    }

    @Override
    public List<Product> getProductsForShoppingCart(int cartID) {
        List<Product> products = new ArrayList<>();

        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            String query = "SELECT i.* FROM Product i "
                    + "JOIN ShoppingCartProduct sci ON i.productID = sci.productID "
                    + "WHERE sci.cartID = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, cartID);
            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = extractProductFromResultSet(rs);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error :" + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> getOrderProduct(int orderID) {
        List<Product> products = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            //Made changes on fetch_single_order_details
            String sql = "{CALL fetch_single_order_details(?)}";
            cs = connection.prepareCall(sql);
            cs.setInt(1, orderID);

            rs = cs.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("productid"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setPicture(rs.getBytes("Picture"));
                product.setDescription(rs.getString("Description"));
                product.setNutrientInformation(rs.getString("NutrientInformation"));
                product.setWarnings(rs.getString("Warnings"));
                product.setCategoryID(rs.getInt("CategoryID"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error getting order products: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> getFavoriteProducts(int customerID) {
        db = DbManager.getInstance();

        List<Product> favoriteProducts = new ArrayList<>();
        connection = db.getConnection();
        try {
            ps = connection.prepareCall("CALL fetch_customer_favorites(?)");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = extractProductFromResultSet(rs);
                favoriteProducts.add(product);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return favoriteProducts;
    }

    @Override
    public boolean createProduct(Product product) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("INSERT INTO Product (Name, Price, FoodCost, TimeCost, Picture, Description, NutrientInformation, Warnings, CategoryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setDouble(3, product.getFoodCost());
            ps.setInt(4, product.getTimeCost());
            ps.setBytes(5, product.getPicture());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getNutrientInformation());
            ps.setString(8, product.getWarnings());
            ps.setInt(9, product.getCategoryID());

            int affectedRows = ps.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Product successfully created
                    System.out.println(affectedRows + ":affected row , successfully added product number: " + product.getID());
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        // Product creation failed
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean retVal = false;
        db = DbManager.getInstance();
        connection = db.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE Product SET Name=?, Price=?, FoodCost=?, TimeCost=?, Picture=?, Description=?, NutrientInformation=?, Warnings=?, CategoryID=? WHERE productID=?");

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setDouble(3, product.getFoodCost());
            ps.setInt(4, product.getTimeCost());
            ps.setBytes(5, product.getPicture());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getNutrientInformation());
            ps.setString(8, product.getWarnings());
            ps.setInt(9, product.getCategoryID());
            ps.setInt(10, product.getID());

            retVal = ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return retVal;

    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Product");
            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("productID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setPicture(rs.getBytes("Picture"));
                product.setDescription(rs.getString("Description"));
                product.setNutrientInformation(rs.getString("NutrientInformation"));
                product.setWarnings(rs.getString("Warnings"));
                product.setCategoryID(rs.getInt("CategoryID"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return products;
    }

    @Override
    public Product getProduct(int productID) {
        Product product = null;
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_single_product(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product();

                product.setID(rs.getInt("productID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setPicture(rs.getBytes("Picture"));
                product.setDescription(rs.getString("Description"));
                product.setNutrientInformation(rs.getString("NutrientInformation"));
                product.setWarnings(rs.getString("Warnings"));
                product.setCategoryID(rs.getInt("CategoryID"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return product;
    }

    @Override
    public int getProductQuantity() {
        int productQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("SELECT COUNT(productid) As quantity FROM Product");
            rs = ps.executeQuery();

            if (rs.next()) {
                productQuantity = rs.getInt("quantity");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return productQuantity;
    }

    @Override
    public boolean deleteProduct(int productID) {
        boolean retVal = false;
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("DELETE FROM Product WHERE productID = ?");
            ps.setInt(1, productID);
            retVal = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return retVal;

    }

    @Override
    public List<Product> getProductsByKeyWord(String keyWord) {
        List<Product> products = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_products_keyword(?)");
            ps.setString(1, keyWord);
            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("productID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setPicture(rs.getBytes("Picture"));
                product.setWarnings(rs.getString("warnings"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setNutrientInformation(rs.getString("nutrientinformation"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> getProductsForOrder(int orderID) {

        try {
            connection = db.getConnection();
            String query = "SELECT p.* FROM order_details od JOIN product p ON od.product_id = p.productid WHERE od.order_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            rs = ps.executeQuery();

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                int productID = rs.getInt("productid");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double foodCost = rs.getDouble("foodcost");
                int timeCost = rs.getInt("timecost");
                byte[] picture = rs.getBytes("picture");
                String description = rs.getString("description");
                String nutrientInformation = rs.getString("nutrientinformation");
                String warnings = rs.getString("warnings");
                int categoryID = rs.getInt("categoryid");

                Product product = new Product(productID, name, price, foodCost, timeCost, picture, description, nutrientInformation, warnings, categoryID);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> getAllProductByCategory(int categoryID) {
        List<Product> products = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL GetProductsByCategory(?)");
            ps.setInt(1, categoryID);
            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("productID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setPicture(rs.getBytes("Picture"));
                product.setWarnings(rs.getString("warnings"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setNutrientInformation(rs.getString("nutrientinformation"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return products;
    }

    private Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product ingredient = new Product();
        ingredient.setID(rs.getInt("productID"));
        ingredient.setName(rs.getString("Name"));
        ingredient.setPrice(rs.getDouble("Price"));
        ingredient.setFoodCost(rs.getDouble("FoodCost"));
        ingredient.setTimeCost(rs.getInt("TimeCost"));
        ingredient.setWarnings(rs.getString("warnings"));
        ingredient.setPicture(rs.getBytes("picture"));
        ingredient.setCategoryID(rs.getInt("categoryID"));
        ingredient.setNutrientInformation(rs.getString("nutrientinformation"));
        ingredient.setDescription(rs.getString("description"));

        return ingredient;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAOImpl();
        File imageFile = new File("C:\\Users\\user\\Desktop\\NewRepo\\topiefor2\\java\\BakerySystem\\src\\main\\webapp\\images\\cake.png");
        try {
            byte[] pictureData = Files.readAllBytes(imageFile.toPath());
            Product product = new Product("Yellow Cake", 54.99, 8.50, 3, pictureData, "High in chocolate", "fibre and calcium", "none", 1);
            // test for add product
            if (productDAO.createProduct(product)) {
                System.out.println("Success");

            } else {
                System.out.println("Failed");

            }

//test for update product
//        if (productDAO.updateProduct(product)) {
//            System.out.println("Successfully updated ");
//
//        } else {
//            System.out.println("Failed");
//
//        }
//Test for getProduct
//        System.out.println(productDAO.getProduct(17));
//byte[] pictureData = product.getPicture();
//Test for getProductQuantity
//        System.out.println(productDAO.getProductQuantity());
//Test for getProductsByKeyWord
//        List<Product> listOfProductByCategory = productDAO.getAllProductByCategory(1);
//        listOfProductByCategory.forEach(product1 -> {
//            System.out.println(product1);
//        });
//        
          } catch (IOException ex) {
             Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

}
