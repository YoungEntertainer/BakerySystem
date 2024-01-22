package za.co.bakerysystem.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.OrderDAO;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.OrderDetails;
import za.co.bakerysystem.model.Payment;
import za.co.bakerysystem.model.Product;

public class OrderDAOImpl implements OrderDAO {

    private static final String JDBC_URL = "jdbc:mysql://your-database-url";
    private static final String USERNAME = "your-username";
    private static final String PASSWORD = "your-password";

    @Override
    public String createOrder(Order order) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO `Order` (Customer_ID, DatePlaced, PickupTime, Fulfilled, Comment, Amount) VALUES(?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters and execute the query
            statement.setInt(1, order.getCustomerID());
            statement.setString(2, order.getDatePlaced().toString());
            statement.setString(3, order.getPickupTime().toString());
            statement.setInt(4, order.getFulfilled());
            statement.setString(5, order.getComment());
            statement.setDouble(6, order.getAmount());

            int affectedRows = statement.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return String.valueOf(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrder(Order order) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE `Order` SET Customer_ID=?, DatePlaced=?,PickupTime=?,Fulfilled=?,Comment=?, Amount=? WHERE ID=?")) {
            // Set parameters and execute the query
            statement.setInt(1, order.getCustomerID());
            statement.setString(2, order.getDatePlaced().toString());
            statement.setString(3, order.getPickupTime().toString());
            statement.setInt(4, order.getFulfilled());
            statement.setString(5, order.getComment());
            statement.setDouble(6, order.getAmount());
            statement.setInt(7, order.getID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fulfillOrder(int orderID, boolean fullFilled) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE `Order` SET Fulfilled=? WHERE ID=?")) {
            statement.setBoolean(1, fullFilled);
            statement.setInt(2, orderID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrderDetail(OrderDetails orderDetails) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO Order_Details (order_id, product_id,PriceAtSale, FoodCostAtSale, Quantity, Comment) VALUES(?,?,?,?,?,?)")) {
            statement.setInt(1, orderDetails.getOrderID());
            statement.setInt(2, orderDetails.getProductID());
            statement.setDouble(3, orderDetails.getPriceAtSale());
            statement.setDouble(4, orderDetails.getFoodCostAtSale());
            statement.setInt(5, orderDetails.getQuantity());
            statement.setString(6, orderDetails.getComment());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `Order`")) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setID(resultSet.getInt("ID"));
                order.setCustomerID(resultSet.getInt("Customer_ID"));

                Timestamp timestamp = resultSet.getTimestamp("DatePlaced");
                if (timestamp != null) {
                    order.setDatePlaced(timestamp.toLocalDateTime());
                }
                Timestamp pickupTimeTimestamp = resultSet.getTimestamp("PickupTime");
                if (pickupTimeTimestamp != null) {
                    order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                }
                order.setFulfilled(resultSet.getInt("Fulfilled"));
                order.setComment(resultSet.getString("Comment"));
                order.setAmount(resultSet.getDouble("Amount"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getLastedOrders() {
        List<Order> lastedOrders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT o.ID, DatePlaced, c.ID as CustomerID, PickupTime, Fulfilled, o.Comment, CustomerName "
                        + "FROM `Order` o JOIN Customer c ON c.ID = Customer_ID WHERE Fulfilled=0")) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setID(resultSet.getInt("ID"));
                order.setCustomerID(resultSet.getInt("CustomerID"));
                Timestamp timestamp = resultSet.getTimestamp("DatePlaced");
                if (timestamp != null) {
                    order.setDatePlaced(timestamp.toLocalDateTime());
                }
                Timestamp pickupTimeTimestamp = resultSet.getTimestamp("PickupTime");
                if (pickupTimeTimestamp != null) {
                    order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                }

                order.setFulfilled(resultSet.getInt("Fulfilled"));
                order.setComment(resultSet.getString("Comment"));
                lastedOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastedOrders;
    }

    @Override
    public int getOrdersCurrent() {
        int quantity = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(ID) as quantity FROM `Order` WHERE Fulfilled=0")) {
            if (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    @Override
    public int getTotalOrdersQuantity() {
        int ordersQuantity = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT SUM(Quantity) as ordersQuantity FROM Order_Details")) {
            if (resultSet.next()) {
                ordersQuantity = resultSet.getInt("ordersQuantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersQuantity;
    }

    @Override
    public List<Order> getOrdersByRange(String startDate, String endDate, String keyWord) {
        List<Order> ordersInRange = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement callableStatement = connection.prepareCall("{CALL fetch_orders_in_range(?,?,?)}")) {
            callableStatement.setString(1, startDate);
            callableStatement.setString(2, endDate);
            callableStatement.setString(3, keyWord);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setID(resultSet.getInt("ID"));
                    order.setCustomerID(resultSet.getInt("Customer_ID"));
                    Timestamp timestamp = resultSet.getTimestamp("DatePlaced");
                    if (timestamp != null) {
                        order.setDatePlaced(timestamp.toLocalDateTime());
                    }
                    Timestamp pickupTimeTimestamp = resultSet.getTimestamp("PickupTime");
                    if (pickupTimeTimestamp != null) {
                        order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                    }
                    order.setFulfilled(resultSet.getInt("Fulfilled"));
                    order.setComment(resultSet.getString("Comment"));
                    order.setAmount(resultSet.getDouble("Amount"));
                    ordersInRange.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersInRange;
    }

    @Override
    public Order getOrder(int orderID) {
        Order order = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement callableStatement = connection.prepareCall("{CALL fetch_single_order(?)}")) {
            callableStatement.setInt(1, orderID);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order();
                    order.setID(resultSet.getInt("ID"));
                    order.setCustomerID(resultSet.getInt("Customer_ID"));
                    Timestamp timestamp = resultSet.getTimestamp("DatePlaced");
                    if (timestamp != null) {
                        order.setDatePlaced(timestamp.toLocalDateTime());
                    }
                    Timestamp pickupTimeTimestamp = resultSet.getTimestamp("PickupTime");
                    if (pickupTimeTimestamp != null) {
                        order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                    }
                    order.setFulfilled(resultSet.getInt("Fulfilled"));
                    order.setComment(resultSet.getString("Comment"));
                    order.setAmount(resultSet.getDouble("Amount"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Payment> getOrderPayment(int orderID) {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement callableStatement = connection.prepareCall("{CALL fetch_single_order_payments(?)}")) {
            callableStatement.setInt(1, orderID);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentTypeID(resultSet.getInt("ID"));
                    payment.setOrderID(resultSet.getInt("Order_ID"));
                    payment.setAmount(resultSet.getDouble("Amount"));
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public List<Product> getOrderProduct(int orderID) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement callableStatement = connection.prepareCall("{CALL fetch_single_order_details(?)}")) {
            callableStatement.setInt(1, orderID);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setID(resultSet.getInt("ID"));
                    product.setName(resultSet.getString("ProductName"));
                    product.setPrice(resultSet.getDouble("Price"));
                    product.setFoodCost(resultSet.getDouble("FoodCost"));
                    product.setTimeCost(resultSet.getInt("TimeCost"));
                    product.setComment(resultSet.getString("Comment"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void deleteOrders(List<Integer> orderIDs) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();

            StringBuilder orderIDString = new StringBuilder();
            for (int i = 0; i < orderIDs.size(); i++) {
                orderIDString.append(orderIDs.get(i));
                if (i < orderIDs.size() - 1) {
                    orderIDString.append(",");
                }
            }

            statement.executeUpdate("DELETE FROM `Order` WHERE ID IN (" + orderIDString.toString() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteOrderDetail(int orderID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Order_Details WHERE order_id = " + orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
