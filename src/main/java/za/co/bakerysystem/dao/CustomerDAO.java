package za.co.bakerysystem.dao;

import za.co.bakerysystem.model.Customer;

import java.util.List;

public interface CustomerDAO {

    boolean createCustomer(Customer customer);

    Customer login(String emailAddress, String password);

    boolean updateCustomer(Customer customer, int customerID);

    List<Customer> getCustomers();

    List<Customer> getTopCustomers(int productID);

    int getCustomersQuantity();

    List<Customer> getCustomersByKeyWord(String keyWord);

    Customer getCustomer(int customerID);

    Customer getCustomerByEmail(String email);

    int getCustomerPoints(int customerID);

    boolean deleteCustomer(int customerIDs);
}
