package za.co.bakerysystem.service.impl;

import za.co.bakerysystem.dao.PaymentDAO;
import za.co.bakerysystem.model.Payment;
import za.co.bakerysystem.model.PaymentType;
import za.co.bakerysystem.service.PaymentService;

import java.util.List;
import za.co.bakerysystem.dao.impl.PaymentDAOImpl;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public boolean createPayment(Payment payment) {
        return paymentDAO.createPayment(payment);
    }

    @Override
    public List<Payment> getOrderPayment(int orderID) {
        return paymentDAO.getOrderPayment(orderID);
    }

    @Override
    public boolean deletePayment(int orderID) {
        return paymentDAO.deletePayment(orderID);
    }

    @Override
    public List<Payment> getOrderPayments(int orderID) {
        return paymentDAO.getOrderPayments(orderID);
    }

    @Override
    public List<PaymentType> getPaymentTypes() {
        return paymentDAO.getPaymentTypes();
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        PaymentDAO paymentDAO = new PaymentDAOImpl();
        PaymentServiceImpl paymentService = new PaymentServiceImpl(paymentDAO);

        // Test createPayment
//        Payment payment = new Payment();
//        payment.setOrderID(2);
//        payment.setAmount(55.0);
//        payment.setPaymentTypeID(3); // Assuming 3 is a valid payment type ID
//        boolean paymentCreated = paymentService.createPayment(payment);
//        System.out.println("Creating Payment: " + paymentCreated);
        // Test getOrderPayments
//        List<Payment> orderPayments = paymentService.getOrderPayments(2); // Assuming 2 is a valid order ID
//        System.out.println("Order Payments: " + orderPayments);
        // Test getPaymentTypes
//        List<PaymentType> paymentTypes = paymentService.getPaymentTypes();
//        System.out.println("Payment Types: " + paymentTypes);
    }
}
