package za.co.bakerysystem.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.OrderDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.OrderDetails;

public class OrderDAOImpl implements OrderDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;
    private CallableStatement callableStatement;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public OrderDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
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
        List<Order> orders = new ArrayList<>();
        connection = DbManager.getInstance().getConnection();
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
    public boolean createOrder(Order order) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO `Order` (Customer_ID, DatePlaced, PickupTime, Fulfilled, Comment, Amount, status) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            // Set parameters
            ps.setInt(1, order.getCustomerID());
            ps.setObject(2, order.getDatePlaced().now());
            ps.setObject(3, order.getPickupTime().now().plusDays(5));
            ps.setInt(4, order.getFulfilled());
            ps.setString(5, order.getComment());
            ps.setDouble(6, order.getAmount());
            ps.setString(7, order.getStatus());

            int affectedRows = ps.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                return generatedKeys.next(); // Return true if there are generated keys
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("UPDATE `Order` SET Customer_ID=?, Fulfilled=?, Comment=?, Amount=?, status=? WHERE ID=?");

            // Set parameters 
            ps.setInt(1, order.getCustomerID());
//            ps.setString(2, order.getDatePlaced().toString());
//            ps.setString(3, order.getPickupTime().toString());
            ps.setInt(2, order.getFulfilled());
            ps.setString(3, order.getComment());
            ps.setDouble(4, order.getAmount());
            ps.setString(5, order.getStatus());
            ps.setInt(6, order.getID());

            int affectedRows = ps.executeUpdate();

            // Check if the update was successful
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating order: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    // Report 1: Orders Placed
    @Override
    public List<Order> getOrdersPlaced(String startDate, String endDate, String sortOrder) {
        List<Order> orders = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "SELECT * FROM `Order` WHERE DatePlaced BETWEEN ? AND ? ORDER BY "
                    + (sortOrder.equals("alphabetical") ? "Customer_ID" : "Category_ID");
            ps = connection.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);

            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                int customerID = rs.getInt("Customer_ID");
                LocalDateTime datePlaced = rs.getTimestamp("DatePlaced").toLocalDateTime();
                LocalDateTime pickupTime = rs.getTimestamp("PickupTime").toLocalDateTime();
                int fulfilled = rs.getInt("Fulfilled");
                String comment = rs.getString("Comment");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("status");

                Order order = new Order(ID, customerID, datePlaced, pickupTime, fulfilled, comment, amount, status);
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting orders placed: " + e.getMessage());
        }
        return orders;
    }

    // Report 2: Orders Outstanding
    @Override
    public List<Order> getOrdersOutstanding(String startDate, String endDate, int category) {
        List<Order> outstandingOrders = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "SELECT o.* FROM `Order` o JOIN `Order_Details` od ON o.ID = od.Order_ID JOIN `Product` p ON od.Product_ID = p.productID  WHERE o.Fulfilled = 0 AND o.DatePlaced BETWEEN ? AND ? AND p.CategoryID = ? ";
            ps = connection.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setInt(3, category);

            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                int customerID = rs.getInt("Customer_ID");
                LocalDateTime datePlaced = rs.getTimestamp("DatePlaced").toLocalDateTime();
                LocalDateTime pickupTime = rs.getTimestamp("PickupTime").toLocalDateTime();
                int fulfilled = rs.getInt("Fulfilled");
                String comment = rs.getString("Comment");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("status");

                Order order = new Order(ID, customerID, datePlaced, pickupTime, fulfilled, comment, amount, status);
                outstandingOrders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting outstanding orders: " + e.getMessage());
        }
        return outstandingOrders;
    }

    // Report 3: Orders Delivered
    @Override
    public List<Order> getOrdersDelivered(String startDate, String endDate, String sortOrder) {
        List<Order> deliveredOrders = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "SELECT * FROM `Order` WHERE Fulfilled = 1 AND DatePlaced BETWEEN ? AND ? ORDER BY "
                    + (sortOrder.equals("alphabetical") ? "Customer_ID" : "Category_ID");
            ps = connection.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);

            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                int customerID = rs.getInt("Customer_ID");
                LocalDateTime datePlaced = rs.getTimestamp("DatePlaced").toLocalDateTime();
                LocalDateTime pickupTime = rs.getTimestamp("PickupTime").toLocalDateTime();
                int fulfilled = rs.getInt("Fulfilled");
                String comment = rs.getString("Comment");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("status");

                Order order = new Order(ID, customerID, datePlaced, pickupTime, fulfilled, comment, amount, status);
                deliveredOrders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting delivered orders: " + e.getMessage());
        }
        return deliveredOrders;
    }

    @Override
    public boolean fulfillOrder(int orderID, int fulfilled) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("UPDATE `Order` SET Fulfilled=? WHERE ID=?");

            // Set parameters
            ps.setInt(1, fulfilled);
            ps.setInt(2, orderID);

            int affectedRows = ps.executeUpdate();

            // Check if the update was successful
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error fulfilling order: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createOrderDetail(OrderDetails orderDetails) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO Order_Details (order_id, product_id, PriceAtSale, FoodCostAtSale, Quantity, Comment) VALUES(?,?,?,?,?,?)");

            ps.setInt(1, orderDetails.getOrderID());
            ps.setInt(2, orderDetails.getProductID());
            ps.setDouble(3, orderDetails.getPriceAtSale());
            ps.setDouble(4, orderDetails.getFoodCostAtSale());
            ps.setInt(5, orderDetails.getQuantity());
            ps.setString(6, orderDetails.getComment());

            ps.executeUpdate();

            // Assuming the insertion was successful if no exception occurred
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating order detail: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());

        }
        return false;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("SELECT * FROM `Order`");
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setID(rs.getInt("ID"));
                order.setCustomerID(rs.getInt("Customer_ID"));

                Timestamp timestamp = rs.getTimestamp("DatePlaced");
                if (timestamp != null) {
                    order.setDatePlaced(timestamp.toLocalDateTime());
                }
                Timestamp pickupTimeTimestamp = rs.getTimestamp("PickupTime");
                if (pickupTimeTimestamp != null) {
                    order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                }
                order.setFulfilled(rs.getInt("Fulfilled"));
                order.setComment(rs.getString("Comment"));
                order.setAmount(rs.getDouble("Amount"));
                order.setStatus(rs.getString("status"));

                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting orders: " + e.getMessage());

            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getLastedOrders() {
        List<Order> lastedOrders = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String sql = "SELECT o.ID, DatePlaced, c.ID as CustomerID, PickupTime, Fulfilled, o.Comment, CustomerName "
                    + "FROM `Order` o JOIN Customer c ON c.ID = Customer_ID WHERE Fulfilled=0";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setID(rs.getInt("ID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                Timestamp timestamp = rs.getTimestamp("DatePlaced");
                if (timestamp != null) {
                    order.setDatePlaced(timestamp.toLocalDateTime());
                }
                Timestamp pickupTimeTimestamp = rs.getTimestamp("PickupTime");
                if (pickupTimeTimestamp != null) {
                    order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                }

                order.setFulfilled(rs.getInt("Fulfilled"));
                order.setComment(rs.getString("Comment"));
                lastedOrders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting lasted orders: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return lastedOrders;
    }

    @Override
    public int getOrdersCurrent() {
        int quantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String sql = "SELECT COUNT(ID) as quantity FROM `Order` WHERE Fulfilled=0";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } catch (SQLException e) {
            System.out.println("Error getting current orders quantity: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return quantity;
    }

    @Override
    public int getTotalOrdersQuantity() {
        int ordersQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String sql = "SELECT SUM(Quantity) as ordersQuantity FROM Order_Details";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                ordersQuantity = rs.getInt("ordersQuantity");
            }
        } catch (SQLException e) {
            System.out.println("Error getting total orders quantity: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return ordersQuantity;
    }

    @Override
    public List<Order> getOrdersByRange(String startDate, String endDate, String keyWord) {
        List<Order> ordersInRange = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String sql = "{CALL fetch_orders_in_range(?,?,?)}";
            ps = connection.prepareCall(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setString(3, keyWord);

            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setID(rs.getInt("order_id"));
                order.setCustomerID(rs.getInt("Customer_ID"));
                Timestamp timestamp = rs.getTimestamp("DatePlaced");
                if (timestamp != null) {
                    order.setDatePlaced(timestamp.toLocalDateTime());
                }
                Timestamp pickupTimeTimestamp = rs.getTimestamp("PickupTime");
                if (pickupTimeTimestamp != null) {
                    order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                }
                order.setFulfilled(rs.getInt("Fulfilled"));
                order.setComment(rs.getString("Comment"));
                order.setAmount(rs.getDouble("Amount"));
                order.setStatus(rs.getString("Status"));
                ordersInRange.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting orders by range: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return ordersInRange;
    }

    @Override
    public Order getOrder(int orderID) {
        Order order = null;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String sql = "{CALL fetch_single_order(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, orderID);

            rs = callableStatement.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setID(rs.getInt("order_id"));
                order.setCustomerID(rs.getInt("Customer_ID"));
                Timestamp timestamp = rs.getTimestamp("DatePlaced");
                if (timestamp != null) {
                    order.setDatePlaced(timestamp.toLocalDateTime());
                }
                Timestamp pickupTimeTimestamp = rs.getTimestamp("PickupTime");
                if (pickupTimeTimestamp != null) {
                    order.setPickupTime(pickupTimeTimestamp.toLocalDateTime());
                }
                order.setFulfilled(rs.getInt("Fulfilled"));
                order.setComment(rs.getString("Comment"));
                order.setAmount(rs.getDouble("Amount"));
                order.setStatus(rs.getString("Status"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting order: " + e.getMessage());
        }
        return order;
    }

    @Override
    public boolean deleteOrder(int orderID) {
        boolean retVal = false;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "DELETE FROM `Order` WHERE ID = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            retVal = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }
        return retVal;
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
        }
        return orders;
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
        }

        return orderQuantity;
    }

    @Override
    public void deleteOrderDetail(int orderID) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String query = "DELETE FROM Order_Details WHERE order_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setID(rs.getInt("OrderID"));
        order.setCustomerID(rs.getInt("customer_ID"));
        order.setDatePlaced(rs.getObject("datePlaced", LocalDateTime.class));
        order.setPickupTime(rs.getObject("pickupTime", LocalDateTime.class));
        order.setFulfilled(rs.getInt("fulfilled"));
        order.setComment(rs.getString("comment"));
        order.setAmount(rs.getDouble("amount"));
        order.setStatus(rs.getString("status"));
        return order;
    }

    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        OrderDAOImpl orderDAO = new OrderDAOImpl();
//
////        // Test createOrder
//        Order orderToCreate = new Order();
//        orderToCreate.setCustomerID(1);
//        orderToCreate.setDatePlaced(LocalDateTime.now());
//        orderToCreate.setPickupTime(LocalDateTime.now().plusHours(3));
//        orderToCreate.setFulfilled(0);
//        orderToCreate.setComment("Get Order");
//        orderToCreate.setAmount(90.0);
//        orderToCreate.setStatus("Delivered");
//        orderToCreate.setID(2);
//
////
//        boolean createOrderResult = orderDAO.createOrder(orderToCreate);
//        System.out.println("Create Order Result: " + createOrderResult);
//        boolean createOrderResult = orderDAO.createOrder(orderToCreate);
//        System.out.println("Create Order Result: " + createOrderResult);
        // Test updateOrder
        Order orderToUpdate = orderDAO.getOrders().get(0); // Assuming there's an order in the database
        orderToUpdate.setComment("Get Comment");

        boolean updateOrderResult = orderDAO.updateOrder(orderToUpdate);
        System.out.println("Update Order Result: " + updateOrderResult);
        // Test fulfillOrder
//        int orderIdToFulfill = orderDAO.getOrders().get(0).getID(); // Assuming there's an order in the database
//        boolean fulfillOrderResult = orderDAO.fulfillOrder(orderIdToFulfill, true);
//        System.out.println("Fulfill Order Result: " + fulfillOrderResult);
        // Test createOrderDetail
        OrderDetails orderDetails = new OrderDetails();
//        orderDetails.setOrderID(orderIdToFulfill);
        orderDetails.setOrderID(4);
        orderDetails.setProductID(2); // Assuming there's a product in the database
        orderDetails.setPriceAtSale(20.0);
        orderDetails.setFoodCostAtSale(15.0);
        orderDetails.setQuantity(2);
        orderDetails.setComment("Test Order Detail");
//

//        OrderDetails orderDetails = new OrderDetails();
//        //orderDetails.setOrderID(orderIdToFulfill);
//        orderDetails.setOrderID(3);
//        orderDetails.setProductID(2); // Assuming there's a product in the database
//        orderDetails.setPriceAtSale(20.0);
//        orderDetails.setFoodCostAtSale(15.0);
//        orderDetails.setQuantity(2);
//        orderDetails.setComment("Test Order Detail");
////
//        boolean createOrderDetailResult = orderDAO.createOrderDetail(orderDetails);
//        System.out.println("Create Order Detail Result: " + createOrderDetailResult);
        // Test getOrders
//        List<Order> allOrders = orderDAO.getOrders();
//        System.out.println("All Orders: " + allOrders);
        // Test getLastedOrders
//        List<Order> lastedOrders = orderDAO.getLastedOrders();
//        System.out.println("Lasted Orders: " + lastedOrders);
        // Test getOrdersCurrent
//        int currentOrdersQuantity = orderDAO.getOrdersCurrent();
//        System.out.println("Current Orders Quantity: " + currentOrdersQuantity);
        // Test getTotalOrdersQuantity
//        int totalOrdersQuantity = orderDAO.getTotalOrdersQuantity();
//        System.out.println("Total Orders Quantity: " + totalOrdersQuantity);
//        // Test getOrdersByRange
//         List<Order> ordersInRange = orderDAO.getOrdersByRange("2024-01-01", "2025-12-31", "test");
//         System.out.println("Orders in Range: " + ordersInRange);
        // Test getOrder
//        Order fetchedOrder = orderDAO.getOrder(1);
//        System.out.println("Fetched Order: " + fetchedOrder);
        // Test getOrderPayment
//        List<Payment> orderPayments = orderDAO.getOrderPayment(1);
//        System.out.println("Order Payments: " + orderPayments);
        //Test Order Placed
//         List<Order> ordersPlaced = orderDAO.getOrdersPlaced("2022-01-01", "2025-12-31", "alphabetical");
//        System.out.println(ordersPlaced);
        // Test getOrderProduct
//        List<Product> orderProducts = orderDAO.getOrderProduct(6);
//        System.out.println("Order Products: " + orderProducts);
        // Test deleteOrder
//        orderDAO.deleteOrder(1);
//        System.out.println("Order Deleted");
        // Test deleteOrderDetail
//        orderDAO.deleteOrderDetail(1);
//        System.out.println("Order Detail Deleted");
    }

}
