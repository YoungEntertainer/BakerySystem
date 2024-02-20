package za.co.bakerysystem.service;

import java.util.List;
import za.co.bakerysystem.exception.paymentType.DuplicatePaymentTypeException;
import za.co.bakerysystem.model.PaymentType;

public interface PaymentTypeService {

    boolean create(PaymentType paymentType);

    PaymentType getById(int id);

    List<PaymentType> getAll();

    boolean update(PaymentType paymentType);

    boolean delete(int id);
    
    boolean exists(String payment_type)throws DuplicatePaymentTypeException;
}
