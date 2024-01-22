
package za.co.bakerysystem.model;


public class Payment {

    private int orderID;
    private int paymentTypeID;
    private double amount;

    public Payment(int orderID, int paymentTypeID, double amount) {
        this.orderID = orderID;
        this.paymentTypeID = paymentTypeID;
        this.amount = amount;
    }

    public Payment() {
    }

    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getPaymentTypeID() {
        return paymentTypeID;
    }

    public void setPaymentTypeID(int paymentTypeID) {
        this.paymentTypeID = paymentTypeID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" + "orderID=" + orderID + ", paymentTypeID=" + paymentTypeID + ", amount=" + amount + '}';
    }

    
}