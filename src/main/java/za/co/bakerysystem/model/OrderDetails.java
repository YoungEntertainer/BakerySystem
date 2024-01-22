
package za.co.bakerysystem.model;


public class OrderDetails {

    private int orderID;
    private int productID;
    private double priceAtSale;
    private double foodCostAtSale;
    private int quantity;
    private String comment;

    public OrderDetails(int orderID, int productID, double priceAtSale, double foodCostAtSale, int quantity, String comment) {
        this.orderID = orderID;
        this.productID = productID;
        this.priceAtSale = priceAtSale;
        this.foodCostAtSale = foodCostAtSale;
        this.quantity = quantity;
        this.comment = comment;
    }

    public OrderDetails() {
    }

    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPriceAtSale() {
        return priceAtSale;
    }

    public void setPriceAtSale(double priceAtSale) {
        this.priceAtSale = priceAtSale;
    }

    public double getFoodCostAtSale() {
        return foodCostAtSale;
    }

    public void setFoodCostAtSale(double foodCostAtSale) {
        this.foodCostAtSale = foodCostAtSale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "OrderDetails{" + "orderID=" + orderID + ", productID=" + productID + ", priceAtSale=" + priceAtSale + ", foodCostAtSale=" + foodCostAtSale + ", quantity=" + quantity + ", comment=" + comment + '}';
    }

    
}