package za.co.bakerysystem.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.OrderDetails;
import za.co.bakerysystem.model.Payment;
import za.co.bakerysystem.model.Product;

public interface OrderDAO {

    String createOrder(Order order);

    void updateOrder(Order order);

    void fulfillOrder(int orderID, boolean fullFilled);

    void createOrderDetail(OrderDetails orderDetails);

    List<Order> getOrders();

    List<Order> getLastedOrders();

    int getOrdersCurrent();

    int getTotalOrdersQuantity();

    List<Order> getOrdersByRange(String startDate, String endDate, String keyWord);

    Order getOrder(int orderID);

    List<Payment> getOrderPayment(int orderID);

    List<Product> getOrderProduct(int orderID);

    void deleteOrders(List<Integer> orderIDs);

    void deleteOrderDetail(int orderID);

}
