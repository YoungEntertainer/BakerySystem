
package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.OrderDetails;

public interface OrderDetailsDAO {

    void save(OrderDetails orderDetails);

    OrderDetails findById(int orderId, int productId);

    List<OrderDetails> findAll();

    void update(OrderDetails orderDetails);

    void delete(int orderId, int productId);
}

