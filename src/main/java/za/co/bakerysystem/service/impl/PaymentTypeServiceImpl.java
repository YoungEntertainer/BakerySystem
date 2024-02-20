package za.co.bakerysystem.service.impl;

import java.util.List;
import za.co.bakerysystem.dao.PaymentTypeDAO;
import za.co.bakerysystem.dao.impl.PaymentTypeDAOImpl;
import za.co.bakerysystem.exception.paymentType.DuplicatePaymentTypeException;
import za.co.bakerysystem.model.PaymentType;
import za.co.bakerysystem.service.PaymentTypeService;

public class PaymentTypeServiceImpl implements PaymentTypeService {

    private PaymentTypeDAO paymentTypeDAO;

    public PaymentTypeServiceImpl(PaymentTypeDAO paymentTypeDAO) {
        this.paymentTypeDAO = paymentTypeDAO;
    }

    @Override
    public boolean create(PaymentType paymentType) {

        return paymentTypeDAO.create(paymentType);
    }

    @Override
    public PaymentType getById(int id) {
        return paymentTypeDAO.getById(id);
    }

    @Override
    public List<PaymentType> getAll() {
        return paymentTypeDAO.getAll();
    }

    @Override
    public boolean update(PaymentType paymentType) {
        return paymentTypeDAO.update(paymentType);
    }

    @Override
    public boolean delete(int id) {
        return paymentTypeDAO.delete(id);
    }

    @Override
    public boolean exists(String payment_type) throws DuplicatePaymentTypeException {
        if (paymentTypeDAO.getAll().stream().anyMatch(paymentType -> paymentType.getType().equalsIgnoreCase(payment_type.toLowerCase()))) {
            throw new DuplicatePaymentTypeException("Payment Type Already available");
        }

        return false;
    }

    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Replace these mock objects with your actual DAO implementations
        PaymentTypeDAO paymentTypeDAO = new PaymentTypeDAOImpl();
        PaymentTypeServiceImpl paymentTypeService = new PaymentTypeServiceImpl(paymentTypeDAO);

        try {
            //        // Test save
//        PaymentType newPaymentType = new PaymentType("Online Transfer");
//        boolean paymentTypeSaved = paymentTypeService.save(newPaymentType);
//        System.out.println("Saving Payment Type: " + paymentTypeSaved);
// Test findById
//        int paymentTypeId = 5; // Replace with a valid payment type ID
//        PaymentType foundPaymentType = paymentTypeService.findById(paymentTypeId);
//        System.out.println("Found Payment Type: " + foundPaymentType);
//        // Test findAll
//        List<PaymentType> allPaymentTypes = paymentTypeService.findAll();
//        System.out.println("All Payment Types: " + allPaymentTypes);
//
//        // Test delete
//        boolean paymentTypeDeleted = paymentTypeService.delete(paymentTypeId);
//        System.out.println("Deleting Payment Type: " + paymentTypeDeleted);

            System.out.println(paymentTypeService.exists("cash"));
        } catch (DuplicatePaymentTypeException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
