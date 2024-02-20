package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.OrderDetailsDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.OrderDetails;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    private Connection connection;
    private static final DbManager db = DbManager.getInstance();
    private PreparedStatement ps;
    private ResultSet rs;

    public OrderDetailsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public OrderDetailsDAOImpl() {
        this.connection = db.getConnection();
    }

    @Override
    public boolean create(OrderDetails orderDetails) {
        connection = db.getConnection();

        try {
            String query = "INSERT INTO order_details (order_Id, product_Id, priceAtSale, foodCostAtSale, quantity, comment) VALUES (?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderDetails.getOrderID());
            ps.setInt(2, orderDetails.getProductID());
            ps.setString(6, orderDetails.getComment());
            ps.setDouble(3, orderDetails.getPriceAtSale());
            ps.setDouble(4, orderDetails.getFoodCostAtSale());
            ps.setInt(5, orderDetails.getQuantity());

            int affectedRows = ps.executeUpdate();

            // Check if the insertion was successful
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public OrderDetails getById(int orderId, int productId) {
        OrderDetails orderDetails = null;
        connection = db.getConnection();

        try {
            String query = "SELECT * FROM Order_details WHERE order_Id = ? AND product_Id = ?";
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);
            ps.setInt(2, productId);

            rs = ps.executeQuery();

            if (rs.next()) {
                orderDetails = extractOrderDetails(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetails> getAll() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        connection = db.getConnection();

        try {
            String query = "SELECT * FROM Order_details";
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetails orderDetails = extractOrderDetails(rs);
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return orderDetailsList;
    }

    @Override
    public boolean update(OrderDetails orderDetails) {
        connection = db.getConnection();

        try {
            String query = "UPDATE Order_details SET quantity = ?, comment = ?, priceAtSale = ?, foodCostAtSale = ? WHERE order_Id = ? AND product_Id = ?";
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderDetails.getQuantity());
            ps.setString(2, orderDetails.getComment());
            ps.setDouble(3, orderDetails.getPriceAtSale());
            ps.setDouble(4, orderDetails.getFoodCostAtSale());
            ps.setInt(5, orderDetails.getOrderID());
            ps.setInt(6, orderDetails.getProductID());

            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }
        return false;
    }

    @Override
    public boolean delete(int orderId, int productId) {
        connection = db.getConnection();

        try {
            String query = "DELETE FROM Order_details WHERE order_Id = ? AND product_Id = ?";
            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);
            ps.setInt(2, productId);

            int affectedRows = ps.executeUpdate();

            // Check if the deletion was successful
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;

    }

    private OrderDetails extractOrderDetails(ResultSet resultSet) throws SQLException {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderID(resultSet.getInt("order_Id"));
        orderDetails.setProductID(resultSet.getInt("product_Id"));
        orderDetails.setQuantity(resultSet.getInt("quantity"));
        orderDetails.setComment(resultSet.getString("comment"));
        orderDetails.setPriceAtSale(resultSet.getDouble("priceAtSale"));
        orderDetails.setFoodCostAtSale(resultSet.getDouble("foodCostAtSale"));
        return orderDetails;
    }

    //.------------------------------------------------------------------------------------------------
    //.------------------------------------------------------------------------------------------------
    //.------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

//        // Test save method
        OrderDetails newOrderDetails = new OrderDetails();

        newOrderDetails.setOrderID(6);
        newOrderDetails.setProductID(2);
        newOrderDetails.setQuantity(5);
        newOrderDetails.setComment("Order Comment");
        newOrderDetails.setPriceAtSale(10.99);
        newOrderDetails.setFoodCostAtSale(5.99);

//        newOrderDetails.setOrderID(2);
//        newOrderDetails.setProductID(2);
//        newOrderDetails.setQuantity(5);
//        newOrderDetails.setComment("Test Comment");
//        newOrderDetails.setPriceAtSale(10.99);
//        newOrderDetails.setFoodCostAtSale(5.99);
//
   //     boolean saveSuccess = orderDetailsDAO.create(newOrderDetails);
   //     System.out.println("Save success: " + saveSuccess);
//        // Test findById method
//        OrderDetails foundOrderDetails = orderDetailsDAO.findById(6, 2);
//        System.out.println("Found OrderDetails: " + foundOrderDetails);
//
//        // Test findAll method

 //              System.out.println("All OrderDetails: " + orderDetailsDAO.getAll());
//        // Test update method
//        newOrderDetails.setQuantity(3);
//        boolean updateSuccess = orderDetailsDAO.update(newOrderDetails);
//        System.out.println("Update success: " + updateSuccess);
        //       System.out.println("All OrderDetails: " + orderDetailsDAO.findAll());
        // Test update method
        // newOrderDetails.setQuantity(10);
        // boolean updateSuccess = orderDetailsDAO.update(newOrderDetails);
        // System.out.println("Update success: " + updateSuccess);
//        // Test delete method
//        boolean deleteSuccess = orderDetailsDAO.delete(6, 2);
//        System.out.println("Delete success: " + deleteSuccess);
//        int orderIDToTest = 3; // Replace with an actual order ID
//
//        List<Product> products = orderDetailsDAO.getProductsForOrder(orderIDToTest);
//
//        if (!products.isEmpty()) {
//            System.out.println("Products for Order ID " + orderIDToTest + ":");
//            for (Product product : products) {
//                System.out.println(product);
//            }
//        } else {
//            System.out.println("No products found for Order ID " + orderIDToTest);
//        }
    }

}
