
package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.PaymentType;

public interface PaymentTypeDAO {

    void save(PaymentType paymentType);

    PaymentType findById(int id);

    List<PaymentType> findAll();

    void update(PaymentType paymentType);

    void delete(int id);
}
