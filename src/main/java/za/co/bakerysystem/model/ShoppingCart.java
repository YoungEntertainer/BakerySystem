package za.co.bakerysystem.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private int cardID;
    private List<Product> products;
    private double totalAmount;

    public ShoppingCart(int cardID, List<Product> products, double totalAmount) {
        this.cardID = cardID;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public ShoppingCart(List<Product> products, double totalAmount) {
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public ShoppingCart() {
        this.products = new ArrayList<Product>();
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + "cardID=" + cardID + ", products=" + products + ", totalAmount=" + totalAmount + '}';
    }

}
