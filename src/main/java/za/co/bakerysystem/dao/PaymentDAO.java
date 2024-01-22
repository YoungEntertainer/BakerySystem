package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.Payment;
import za.co.bakerysystem.model.PaymentType;

public interface PaymentDAO {

    void createPayment(Payment payment);

    void deletePayment(int orderID);

    List<Payment> getOrderPayments(int orderID);

    List<PaymentType> getPaymentTypes();
}
