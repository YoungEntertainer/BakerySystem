package za.co.bakerysystem.service;

import java.util.List;
import za.co.bakerysystem.exception.customer.CustomerDeletionException;
import za.co.bakerysystem.exception.customer.CustomerNotFoundException;
import za.co.bakerysystem.exception.customer.DuplicateEmailException;
import za.co.bakerysystem.exception.customer.DuplicateIdException;
import za.co.bakerysystem.model.Customer;

public interface CustomerService {

    boolean createCustomer(Customer customer);

    Customer login(String emailAddress, String password);

    List<Customer> getTopCustomers(int productID);

    boolean updateCustomer(Customer customer, int customerID);

    List<Customer> getCustomers();

    int getCustomersQuantity();

    List<Customer> getCustomersByKeyWord(String keyWord);

    Customer getCustomer(int customerID) throws CustomerNotFoundException;

    int getCustomerPoints(int customerID);

    Customer getCustomerByEmail(String email);

    boolean deleteCustomer(int customerIDs) throws CustomerNotFoundException, CustomerDeletionException;

    boolean exists(String email, String ID) throws DuplicateEmailException, DuplicateIdException;
}
