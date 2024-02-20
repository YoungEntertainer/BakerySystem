package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.Product;

public interface ProductDAO {

    boolean createProduct(Product product);

    boolean updateProduct(Product product);

    List<Product> getAllProductByCategory(int categoryID);

    List<Product> getRelatedProducts(int ingredientID);

    List<Product> getFavoriteProducts(int customerID);

    List<Product> getOrderProduct(int orderID);

    List<Product> getProductsForOrder(int orderID);

    List<Product> getProductsForShoppingCart(int cartID);

    List<Product> getProducts();

    List<Product> getProductsByKeyWord(String keyWord);

    Product getProduct(int productID);

    int getProductQuantity();

    boolean deleteProduct(int productID);
    
   
}
