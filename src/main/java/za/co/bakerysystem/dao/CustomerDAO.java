package za.co.bakerysystem.dao;

import java.time.LocalDate;
import za.co.bakerysystem.model.Customer;

import java.util.List;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.Product;

public interface CustomerDAO {

    boolean createCustomer(Customer customer);

    boolean updateCustomer(Customer customer, int customerID);

    List<Customer> getCustomers();

    int getCustomersQuantity();

    List<Customer> getCustomersByKeyWord(String keyWord);

    Customer getCustomer(int customerID);

    List<Product> getFavoriteProducts(int customerID);

    int getCustomerPoints(int customerID);

    List<Order> getCustomerOrders(int customerID);

    int getNumOrders(int customerID);

    List<Order> getOrdersByRange(int fulfilled, LocalDate startDate, LocalDate endDate);

    boolean deleteCustomer(int customerIDs);
}
