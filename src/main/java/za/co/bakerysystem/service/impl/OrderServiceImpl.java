package za.co.bakerysystem.service.impl;

import java.time.LocalDate;
import java.util.List;
import za.co.bakerysystem.dao.OrderDAO;
import za.co.bakerysystem.dao.impl.OrderDAOImpl;
import za.co.bakerysystem.exception.order.OrderDeletionException;
import za.co.bakerysystem.exception.order.OrderNotFoundException;
import za.co.bakerysystem.exception.order.OrderUpdateException;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.OrderDetails;
import za.co.bakerysystem.service.OrderService;

public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public boolean createOrder(Order order) {
        return orderDAO.createOrder(order);
    }

    @Override
    public boolean updateOrder(Order order) throws OrderUpdateException, OrderNotFoundException {
//        throws OrderUpdateException, OrderNotFoundException 
        try {
            Order existingOrder = getOrder(order.getID());
            return orderDAO.updateOrder(order);
        } catch (OrderNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OrderUpdateException("Error updating order: " + e.getMessage());
        }

    }

    @Override
    public int getOrderQuantityByKeyWord(String keyWord) {
        return orderDAO.getOrderQuantityByKeyWord(keyWord);
    }

    @Override
    public int getOrderQuantity(int productID) {
        return orderDAO.getOrderQuantity(productID);
    }

    @Override
    public List<Order> getOrders(int productID) {
        return orderDAO.getOrders(productID);
    }

    @Override
    public List<Order> getCustomerOrders(int customerID) {
        return orderDAO.getCustomerOrders(customerID);
    }

    @Override
    public int getNumOrders(int customerID) {

        return orderDAO.getNumOrders(customerID);
    }

    @Override
    public List<Order> getOrdersByRange(int fulfilled, LocalDate startDate, LocalDate endDate) {
        return orderDAO.getOrdersByRange(fulfilled, startDate, endDate);
    }

    @Override
    public List<Order> getOrdersPlaced(String startDate, String endDate, String sortOrder) {
        return orderDAO.getOrdersPlaced(startDate, endDate, sortOrder);
    }

    @Override
    public List<Order> getOrdersOutstanding(String startDate, String endDate, int category) {
        return orderDAO.getOrdersOutstanding(startDate, endDate, category);
    }

    @Override
    public List<Order> getOrdersDelivered(String startDate, String endDate, String sortOrder) {
        return orderDAO.getOrdersDelivered(startDate, endDate, sortOrder);
    }

    @Override
    public boolean fulfillOrder(int orderID, int fulfilled) {
        return orderDAO.fulfillOrder(orderID, fulfilled);
    }

    @Override
    public boolean createOrderDetail(OrderDetails orderDetails) {
        return orderDAO.createOrderDetail(orderDetails);
    }

    @Override
    public List<Order> getOrders() {
        return orderDAO.getOrders();
    }

    @Override
    public List<Order> getLastedOrders() {
        return orderDAO.getLastedOrders();
    }

    @Override
    public int getOrdersCurrent() {
        return orderDAO.getOrdersCurrent();
    }

    @Override
    public int getTotalOrdersQuantity() {
        return orderDAO.getTotalOrdersQuantity();
    }

    @Override
    public List<Order> getOrdersByRange(String startDate, String endDate, String keyWord) {
        return orderDAO.getOrdersByRange(startDate, endDate, keyWord);
    }

    @Override
    public Order getOrder(int orderID) throws OrderNotFoundException {

        if (orderDAO.getOrder(orderID) != null) {
            return orderDAO.getOrder(orderID);
        }
        throw new OrderNotFoundException("Order " + orderID + " not found");

    }

    @Override
    public boolean deleteOrder(int orderID) throws OrderDeletionException, OrderNotFoundException {

        try {
            Order order = getOrder(orderID);
            return orderDAO.deleteOrder(orderID);
        } catch (OrderNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OrderDeletionException("Error deleting order");
        }

    }

    @Override
    public void deleteOrderDetail(int orderID) {
        orderDAO.deleteOrderDetail(orderID);
    }

    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderServiceImpl orderService = new OrderServiceImpl(orderDAO);

//        // Create a sample Order
        Order order = new Order();
        order.setID(3);
        order.setCustomerID(2);
//        order.setDatePlaced(LocalDateTime.now());
        order.setFulfilled(0);
        order.setComment("Hello Order");
        order.setStatus("Progress");
//
//        // Create a sample OrderDetail
//        OrderDetails orderDetails = new OrderDetails();
//        orderDetails.setOrderID(6);
//        orderDetails.setProductID(6);
//        orderDetails.setPriceAtSale(10.99);
//        orderDetails.setFoodCostAtSale(5.99);
//        orderDetails.setQuantity(2);
//        orderDetails.setComment("Ordered");
//        try {
        //Test Update the order
//        orderService.updateOrder(order);
        //Test getOrderByID
//        System.out.println(orderService.getOrder(2));
// Test creating an order
//System.out.println("Creating Order: " + orderService.createOrder(order));
//        // Test fulfilling an order
//  System.out.println("Fulfilling Order: " + orderService.fulfillOrder(6, 1));
// Test creating an order detail
//  System.out.println("Creating Order Detail: " + orderService.createOrderDetail(orderDetails));
//        // Test getting orders
//        List<Order> orders = orderService.getOrders();
//        System.out.println("Orders: " + orders);
// Test getting order payment
//        List<Payment> orderPayment = orderService.getOrderPayment(2);
//        System.out.println("Order Payment: " + orderPayment);
// Test getting order product
//        List<Product> orderProduct = orderService.getOrderProduct(6);
//        System.out.println("Order Product: " + orderProduct);
//        // Test deleting an order
//        orderService.deleteOrder(o6);
//
//        // Test deleting an order detail
//        orderService.deleteOrderDetail(orderDetails.getOrderID());
//        // Test Orders Placed
//        System.out.println("Report: Orders Placed");
//        List<Order> ordersPlaced = orderService.getOrdersPlaced("2022-01-01", "2025-12-31", "alphabetical");
//        System.out.println(ordersPlaced);
//        // Test Orders Outstanding
//        System.out.println("Report: Orders Outstanding");
//        List<Order> ordersOutstanding = orderService.getOrdersOutstanding("2022-01-01", "2025-12-31", 1);
//        System.out.println(ordersOutstanding);
// Test Orders Delivered
//        System.out.println("Report: Orders Delivered");
//        List<Order> ordersDelivered = orderService.getOrdersDelivered("2022-01-01", "2025-12-31", "alphabetical");
//        System.out.println(ordersDelivered);
//        } catch (OrderUpdateException ex) {
//            System.out.println(ex.toString());
//        } catch (OrderNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        }
    }
}
