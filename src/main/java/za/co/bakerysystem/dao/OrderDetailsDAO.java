package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.OrderDetails;

public interface OrderDetailsDAO {

    boolean create(OrderDetails orderDetails);

    OrderDetails getById(int orderId, int productId);

    List<OrderDetails> getAll();

    boolean update(OrderDetails orderDetails);

    boolean delete(int orderId, int productId);

}
