package za.co.bakerysystem.service;

import java.util.List;
import za.co.bakerysystem.model.OrderDetails;

public interface OrderDetailsService {

    boolean saveOrderDetails(OrderDetails orderDetails);

    OrderDetails findOrderDetailsById(int orderId, int productId);

    List<OrderDetails> findAllOrderDetails();

    boolean updateOrderDetails(OrderDetails orderDetails);

    boolean deleteOrderDetails(int orderId, int productId);
}
