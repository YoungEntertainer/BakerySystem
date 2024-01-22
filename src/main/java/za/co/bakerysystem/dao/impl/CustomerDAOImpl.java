package za.co.bakerysystem.dao.impl;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.CustomerDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Customer;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.Product;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;

    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public CustomerDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();

    }

    @Override
    public boolean createCustomer(Customer customer) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO Customer (CustomerName, customerIDNo, phoneNumber, joinDate, addressOne, addressTwo, city, zip, comment,email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerIDNo());
            ps.setString(3, customer.getPhoneNumber());
            ps.setObject(4, customer.getJoinDate());
            ps.setString(5, customer.getAddressOne());
            ps.setString(6, customer.getAddressTwo());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getZip());
            ps.setString(9, customer.getComment());
            ps.setString(10, customer.getEmail());
            ps.setString(11, customer.getPassword());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    customer.setID(generatedID);
                    return true; // Return true if the customer creation was successful
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false; // Return false if an exception occurs during customer creation
        }
    }

    @Override
    public boolean updateCustomer(Customer customer, int customerID) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("UPDATE Customer SET customerName=?, customerIDNo=?, phoneNumber=?, joinDate=?, addressOne=?, addressTwo=?, city=?, zip=?, comment=? ,email=? ,password=? WHERE ID=?");
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerIDNo());
            ps.setString(3, customer.getPhoneNumber());
            ps.setObject(4, customer.getJoinDate());
            ps.setString(5, customer.getAddressOne());
            ps.setString(6, customer.getAddressTwo());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getZip());
            ps.setString(9, customer.getComment());
            ps.setInt(12, customerID);
            ps.setString(10, customer.getEmail());
            ps.setString(11, customer.getPassword());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating customer failed, no rows affected.");
            }

            return true; // Return true if the customer update was successful

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false; // Return false if an exception occurs during customer update
        }
    }

    @Override
    public List<Customer> getCustomers() {
        db = DbManager.getInstance();
        List<Customer> customers = new ArrayList<>();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("SELECT * FROM Customer");
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = extractCustomerFromResultSet(rs);
                customers.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return customers;
    }

    @Override
    public int getCustomersQuantity() {
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("SELECT COUNT(ID) AS quantity FROM Customer");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return 0;
    }

    @Override
    public List<Customer> getCustomersByKeyWord(String keyWord) {
        db = DbManager.getInstance();

        List<Customer> customers = new ArrayList<>();
        connection = db.getConnection();
        try {
            ps = connection.prepareCall("CALL fetch_customers_keyword(?)");
            ps.setString(1, keyWord);
            rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = extractCustomerFromResultSet(rs);
                customers.add(customer);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return customers;
    }

    @Override
    public Customer getCustomer(int customerID) {
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareCall("CALL fetch_customer_info(?)");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return null;
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
    public int getCustomerPoints(int customerID) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareCall("CALL fetch_customer_points(?)");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("points");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return 0;
    }

    @Override
    public List<Order> getCustomerOrders(int customerID) {
        db = DbManager.getInstance();

        List<Order> customerOrders = new ArrayList<>();
        connection = db.getConnection();
        try {
            ps = connection.prepareCall("CALL fetch_customer_orders(?)");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = extractOrderFromResultSet(rs);
                customerOrders.add(order);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return customerOrders;
    }

    @Override
    public int getNumOrders(int customerID) {
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {
            ps = connection.prepareStatement("SELECT COUNT(ID) AS numOrder FROM `Order` o WHERE Customer_ID = ?");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("numOrder");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return 0;
    }

    @Override
    public List<Order> getOrdersByRange(int fulfilled, LocalDate startDate, LocalDate endDate) {
        db = DbManager.getInstance();
        List<Order> orders = new ArrayList<>();
        connection = db.getConnection();
        try {
            ps = connection.prepareCall("CALL fetch_select_orders_in_range(?, ?, ?)");
            ps.setInt(1, fulfilled);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));

            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = extractOrderFromResultSet(rs);
                orders.add(order);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

        return orders;
    }

    @Override
    public boolean deleteCustomer(int customerID) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Customer WHERE ID = ?")) {
            ps.setInt(1, customerID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setID(rs.getInt("ID"));
        customer.setCustomerName(rs.getString("customerName"));
        customer.setCustomerIDNo(rs.getString("customerIDNo"));
        customer.setPhoneNumber(rs.getString("phoneNumber"));
        customer.setJoinDate(rs.getObject("joinDate", LocalDateTime.class));
        customer.setAddressOne(rs.getString("addressOne"));
        customer.setAddressTwo(rs.getString("addressTwo"));
        customer.setCity(rs.getString("city"));
        customer.setZip(rs.getString("zip"));
        customer.setComment(rs.getString("comment"));
        customer.setPassword(rs.getString("password"));
        customer.setEmail(rs.getString("email"));

        return customer;
    }

    private Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setID(rs.getInt("ID"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        product.setFoodCost(rs.getDouble("foodCost"));
        product.setTimeCost(rs.getInt("timeCost"));
        product.setComment(rs.getString("comment"));
        return product;
    }

    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setID(rs.getInt("ID"));
        order.setCustomerID(rs.getInt("customerID"));
        order.setDatePlaced(rs.getObject("datePlaced", LocalDateTime.class));
        order.setPickupTime(rs.getObject("pickupTime", LocalDateTime.class));
        order.setFulfilled(rs.getInt("fulfilled"));
        order.setComment(rs.getString("comment"));
        order.setAmount(rs.getDouble("amount"));
        return order;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        //db = DbManager.getInstance();
        CustomerDAO customerDAO = new CustomerDAOImpl();

        // Test createCustomer method
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("Sphe Doe");
        newCustomer.setCustomerIDNo("123456789");
        newCustomer.setPhoneNumber("555-1234");
        newCustomer.setJoinDate(LocalDateTime.now());
        newCustomer.setAddressOne("123 Main St");
        newCustomer.setCity("Pretoria");
        newCustomer.setZip("12345");
        newCustomer.setComment("A new customer");
        newCustomer.setEmail("john@example.com");
        newCustomer.setPassword("password");

     //   Adding customer
         customerDAO.createCustomer(newCustomer);
         
        //update customer
//        if (customerDAO.updateCustomer(newCustomer,2)) {
//            System.out.println("Success");
//        } else {
//            System.out.println("failed");
//
//        }
        //Delete customer
//        if (customerDAO.deleteCustomer(3)) {
//            System.out.println("Success");
//        } else {
//            System.out.println("failed");
//
//        }
        //System.out.println("New customer created with ID: " + newCustomer.getID());
//        // Test updateCustomer method
//        newCustomer.setCustomerName("John Updated");
//        customerDAO.updateCustomer(newCustomer);
//        System.out.println("Customer updated successfully.");
//
//        // Test getCustomers method
//        List<Customer> customers = customerDAO.getCustomers();
//        System.out.println("All customers: " + customers);
//
//        // Test getCustomersQuantity method
//        int customersQuantity = customerDAO.getCustomersQuantity();
//        System.out.println("Number of customers: " + customersQuantity);
//
//        // Test getCustomersByKeyWord method
        String keyword = "Pretoria";
        List<Customer> customersByKeyword = customerDAO.getCustomersByKeyWord(keyword);
        System.out.println("Customers with keyword '" + keyword + "': " + customersByKeyword);
//
//        // Test getCustomer method
//        int customerIdToRetrieve = 2;
//        Customer retrievedCustomer = customerDAO.getCustomer(customerIdToRetrieve);
//        System.out.println("Retrieved customer by ID " + customerIdToRetrieve + ": " + retrievedCustomer);
//
//        // Test getFavoriteProducts method
//        List<Product> favoriteProducts = customerDAO.getFavoriteProducts(customerIdToRetrieve);
//        System.out.println("Favorite products for customer ID " + customerIdToRetrieve + ": " + favoriteProducts);
//
//        // Test getCustomerPoints method
//        int customerPoints = customerDAO.getCustomerPoints(customerIdToRetrieve);
//        System.out.println("Customer points for customer ID " + customerIdToRetrieve + ": " + customerPoints);
//
//        // Test getCustomerOrders method
//        List<Order> customerOrders = customerDAO.getCustomerOrders(customerIdToRetrieve);
//        System.out.println("Orders for customer ID " + customerIdToRetrieve + ": " + customerOrders);
//
//        // Test getNumOrders method
//        int numOrders = customerDAO.getNumOrders(customerIdToRetrieve);
//        System.out.println("Number of orders for customer ID " + customerIdToRetrieve + ": " + numOrders);
//
//        // Test getOrdersByRange method
//        boolean fulfilled = true;
//        LocalDate startDate = LocalDate.now().minusMonths(1);
//        LocalDate endDate = LocalDate.now();
//        List<Order> ordersInRange = customerDAO.getOrdersByRange(fulfilled, startDate, endDate);
//        System.out.println("Orders within the date range: " + ordersInRange);
        // Test deleteCustomers method
//List<String> customerIdsToDelete = new ArrayList<>();
//customerIdsToDelete.add(newCustomer.getID());
//customerDAO.deleteCustomers(customerIdsToDelete);
//System.out.println("Customers deleted successfully.");
    }

}
