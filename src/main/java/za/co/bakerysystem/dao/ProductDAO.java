
package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.Customer;
import za.co.bakerysystem.model.Order;
import za.co.bakerysystem.model.Product;

public interface ProductDAO {

    boolean createProduct(Product product);

    boolean updateProduct(Product product);

    List<Product> getProducts();

    List<Product> getProductsByKeyWord(String keyWord);

    int getOrderQuantity(int productID);

    int getSaleQuantity(int productID);

    int getOrderQuantityByKeyWord(String keyWord);

    List<Order> getOrders(int productID);

    List<Customer> getTopCustomers(int productID);

    Product getProduct(int productID);

    int getProductQuantity();

    List<String> getRecipe(int productID);

    void deleteProduct(int productID);
}
