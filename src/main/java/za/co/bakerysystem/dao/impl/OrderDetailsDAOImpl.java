
package za.co.bakerysystem.dao.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.OrderDetailsDAO;
import za.co.bakerysystem.model.OrderDetails;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    // You need to provide the database connection details here
    private static final String JDBC_URL = "your_jdbc_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public void save(OrderDetails orderDetails) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO OrderDetails (orderId, productId, quantity, comment, priceAtSale, foodCostAtSale) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setInt(1, orderDetails.getOrderID());
            statement.setInt(2, orderDetails.getProductID());
            statement.setInt(3, orderDetails.getQuantity());
            statement.setString(4, orderDetails.getComment());
            statement.setDouble(5, orderDetails.getPriceAtSale());
            statement.setDouble(6, orderDetails.getFoodCostAtSale());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderDetails findById(int orderId, int productId) {
        OrderDetails orderDetails = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM OrderDetails WHERE orderId = ? AND productId = ?")) {

            statement.setInt(1, orderId);
            statement.setInt(2, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    orderDetails = extractOrderDetails(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetails> findAll() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrderDetails");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                OrderDetails orderDetails = extractOrderDetails(resultSet);
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetailsList;
    }

    @Override
    public void update(OrderDetails orderDetails) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE OrderDetails SET quantity = ?, comment = ?, priceAtSale = ?, foodCostAtSale = ? WHERE orderId = ? AND productId = ?")) {

            statement.setInt(1, orderDetails.getQuantity());
            statement.setString(2, orderDetails.getComment());
            statement.setDouble(3, orderDetails.getPriceAtSale());
            statement.setDouble(4, orderDetails.getFoodCostAtSale());
            statement.setInt(5, orderDetails.getOrderID());
            statement.setInt(6, orderDetails.getProductID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int orderId, int productId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM OrderDetails WHERE orderId = ? AND productId = ?")) {

            statement.setInt(1, orderId);
            statement.setInt(2, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderDetails extractOrderDetails(ResultSet resultSet) throws SQLException {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderID(resultSet.getInt("orderId"));
        orderDetails.setProductID(resultSet.getInt("productId"));
        orderDetails.setQuantity(resultSet.getInt("quantity"));
        orderDetails.setComment(resultSet.getString("comment"));
        orderDetails.setPriceAtSale(resultSet.getDouble("priceAtSale"));
        orderDetails.setFoodCostAtSale(resultSet.getDouble("foodCostAtSale"));
        return orderDetails;
    }

 
}
