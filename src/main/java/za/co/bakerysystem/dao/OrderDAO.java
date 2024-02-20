package za.co.bakerysystem.dao;

import java.time.LocalDate;
import java.util.List;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.OrderDetails;

public interface OrderDAO {

    boolean createOrder(Order order);

    boolean updateOrder(Order order);

    boolean fulfillOrder(int orderID, int fullFilled);

    boolean createOrderDetail(OrderDetails orderDetails);

    List<Order> getOrders();

    List<Order> getLastedOrders();

    int getOrdersCurrent();

    List<Order> getCustomerOrders(int customerID);

    int getNumOrders(int customerID);

    List<Order> getOrdersByRange(int fulfilled, LocalDate startDate, LocalDate endDate);

    int getOrderQuantity(int productID);

    int getOrderQuantityByKeyWord(String keyWord);

    List<Order> getOrders(int productID);

    int getTotalOrdersQuantity();

    List<Order> getOrdersByRange(String startDate, String endDate, String keyWord);

    Order getOrder(int orderID);

    List<Order> getOrdersPlaced(String startDate, String endDate, String sortOrder);

    List<Order> getOrdersOutstanding(String startDate, String endDate, int category);

    List<Order> getOrdersDelivered(String startDate, String endDate, String sortOrder);

    boolean deleteOrder(int orderID);

    void deleteOrderDetail(int orderID);

}
