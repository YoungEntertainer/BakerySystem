package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Customer;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.Product;

public class ProductDAOImpl implements ProductDAO {

    // You need to provide the database connection details here
    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;

    public ProductDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public boolean createProduct(Product product) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("INSERT INTO Product (Name, Price, FoodCost, TimeCost, Comment, Description, NutrientInformation, Warnings, CategoryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setDouble(3, product.getFoodCost());
            ps.setInt(4, product.getTimeCost());
            ps.setString(5, product.getComment());
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
                    System.out.println(affectedRows + ":affected row , successfully aded product number: " + product.getID());
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
            ps = connection.prepareStatement("UPDATE Product SET Name=?, Price=?, FoodCost=?, TimeCost=?, Comment=?, Description=?, NutrientInformation=?, Warnings=?, CategoryID=? WHERE ID=?");

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setDouble(3, product.getFoodCost());
            ps.setInt(4, product.getTimeCost());
            ps.setString(5, product.getComment());
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
                product.setID(rs.getInt("ID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setComment(rs.getString("Comment"));
                product.setDescription(rs.getString("Description"));
                product.setNutrientInformation(rs.getString("NutrientInformation"));
                product.setWarnings(rs.getString("Warnings"));
                product.setCategoryID(rs.getInt("CategoryID"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return products;
    }

// Helper method to close the connection
    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    @Override
    public int getOrderQuantityByKeyWord(String keyWord) {
        int orderQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_product_quantity_Keyword(?)");
            ps.setString(1, keyWord);
            rs = ps.executeQuery();

            if (rs.next()) {
                orderQuantity = rs.getInt("orderQuantity");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return orderQuantity;
    }

    @Override
    public List<Order> getOrders(int productID) {
        List<Order> orders = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_product_orders(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setID(rs.getInt("orderID"));
                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Customer> getTopCustomers(int productID) {
        List<Customer> topCustomers = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_product_top_customer(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setID(rs.getInt("customerID"));
                topCustomers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return topCustomers;
    }

    @Override
    public Product getProduct(int productID) {
        Product product = new Product();
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CALL fetch_single_product(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            if (rs.next()) {
                product.setID(rs.getInt("ID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setComment(rs.getString("Comment"));
                product.setDescription(rs.getString("Description"));
                product.setNutrientInformation(rs.getString("NutrientInformation"));
                product.setWarnings(rs.getString("Warnings"));
                product.setCategoryID(rs.getInt("CategoryID"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return product;
    }

    @Override
    public int getProductQuantity() {
        int productQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("SELECT COUNT(ID) As quantity FROM Product");
            rs = ps.executeQuery();

            if (rs.next()) {
                productQuantity = rs.getInt("quantity");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return productQuantity;
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
                recipe.add(rs.getString("ingredient"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return recipe;
    }

    @Override
    public void deleteProduct(int productID) {
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("DELETE FROM Product WHERE ID = ?");
            ps.setInt(1, productID);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
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
                product.setID(rs.getInt("ID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setFoodCost(rs.getDouble("FoodCost"));
                product.setTimeCost(rs.getInt("TimeCost"));
                product.setComment(rs.getString("Comment"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return products;
    }

    @Override
    public int getOrderQuantity(int productID) {
        int orderQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("CALL fetch_product_quantity(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            if (rs.next()) {
                orderQuantity = rs.getInt("orderQuantity");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return orderQuantity;
    }

    @Override
    public int getSaleQuantity(int productID) {
        int saleQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("CALL fetch_product_quantity_sale(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            if (rs.next()) {
                saleQuantity = rs.getInt("saleQuantity");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
        }
        return saleQuantity;
    }

    private void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error closing result set: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAOImpl();
        Product product = new Product(3, "Black Cake", 54.99, 8.50, 3, "Delicious cake baked by our own chefs.", "High in chocolate", "fibre and calcium", "none", 1);
        //test for add product
        //        if (productDAO.createProduct(product)) {
        //            System.out.println("Success");
        //
        //        } else {
        //            System.out.println("Failed");
        //
        //        }

        //test for update product
        //        if (productDAO.updateProduct(product)) {
        //            System.out.println("Successfully updated ");
        //
        //        } else {
        //            System.out.println("Failed");
        //
        //        }
        //Test for getProduct 
        //System.out.println(productDAO.getProduct(3));
        
        //Test for getProductQuantity
//        System.out.println(productDAO.getProductQuantity());
        //Test for getProductsByKeyWord
//        List<Product> listOfProductByKeyWord = productDAO.getProductsByKeyWord("Black");
        
//        listOfProductByKeyWord.forEach(product1 -> {
//            System.out.println(product1);
//        });
//        

    }
}
