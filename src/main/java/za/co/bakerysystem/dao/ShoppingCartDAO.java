package za.co.bakerysystem.dao;

import za.co.bakerysystem.model.Product;
import za.co.bakerysystem.model.ShoppingCart;

public interface ShoppingCartDAO {

    ShoppingCart getShoppingCartById(int cartID);

    boolean addProductToCart(int cartID, Product product, int quantity);

    boolean removeProductFromCart(int cartID, Product product);

    boolean removeProductFromCart(ShoppingCart shoppingCart, Product product);

    boolean updateShoppingCartQuantity(int cartID, int productID, int newQuantity);

    double calculateTotalAmount(int cartID);

}
