package za.co.bakerysystem.service.impl;

import za.co.bakerysystem.dao.OrderDetailsDAO;
import za.co.bakerysystem.model.OrderDetails;
import za.co.bakerysystem.service.OrderDetailsService;

import java.util.List;
import za.co.bakerysystem.dao.impl.OrderDetailsDAOImpl;

public class OrderDetailsServiceImpl implements OrderDetailsService {

    private OrderDetailsDAO orderDetailsDAO;

    public OrderDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO) {
        this.orderDetailsDAO = orderDetailsDAO;
    }

    @Override
    public boolean saveOrderDetails(OrderDetails orderDetails) {
        return orderDetailsDAO.create(orderDetails);
    }

    @Override
    public OrderDetails findOrderDetailsById(int orderId, int productId) {
        return orderDetailsDAO.getById(orderId, productId);
    }

    @Override
    public List<OrderDetails> findAllOrderDetails() {
        return orderDetailsDAO.getAll();
    }

    @Override
    public boolean updateOrderDetails(OrderDetails orderDetails) {
        return orderDetailsDAO.update(orderDetails);
    }

    @Override
    public boolean deleteOrderDetails(int orderId, int productId) {
        return orderDetailsDAO.delete(orderId, productId);
    }

    public static void main(String[] args) {
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();
        OrderDetailsService orderDetailsService = new OrderDetailsServiceImpl(orderDetailsDAO);

        // Test saveOrderDetails
//        OrderDetails newOrderDetails = new OrderDetails(3,5,5.7,7.6,10,"comme");
//        boolean saved = orderDetailsService.saveOrderDetails(newOrderDetails);
//        System.out.println("OrderDetails saved: " + saved);
        // Test findOrderDetailsById
//        int orderIdToFind = 3; // Replace with an existing order ID
//        int productIdToFind = 5; // Replace with an existing product ID
//        OrderDetails foundOrderDetails = orderDetailsService.findOrderDetailsById(orderIdToFind, productIdToFind);
//        System.out.println("OrderDetails found by ID (" + orderIdToFind + ", " + productIdToFind + "): " + foundOrderDetails);
//        // Test findAllOrderDetails
//        List<OrderDetails> allOrderDetails = orderDetailsService.findAllOrderDetails();
//        System.out.println("All OrderDetails: " + allOrderDetails);
        // Test deleteOrderDetails
//        int orderIdToDelete = 3; // Replace with an existing order ID
//        int productIdToDelete = 5; // Replace with an existing product ID
//        boolean deleted = orderDetailsService.deleteOrderDetails(orderIdToDelete, productIdToDelete);
//        System.out.println("OrderDetails deleted: " + deleted);
    }
}
