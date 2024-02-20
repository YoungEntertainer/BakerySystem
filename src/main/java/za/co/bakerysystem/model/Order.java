package za.co.bakerysystem.model;

import java.time.LocalDateTime;

public class Order {

    private int ID;
    private int customerID;
    private LocalDateTime datePlaced;
    private LocalDateTime pickupTime;
    private int fulfilled;
    private String comment;
    private double amount;
    private String status;

    public Order(int ID, int customerID, LocalDateTime datePlaced, LocalDateTime pickupTime, int fulfilled, String comment, double amount, String status) {
        this.ID = ID;
        this.customerID = customerID;
        this.datePlaced = datePlaced;
        this.pickupTime = pickupTime;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.amount = amount;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public LocalDateTime getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(LocalDateTime datePlaced) {
        this.datePlaced = datePlaced;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(int fulfilled) {
        this.fulfilled = fulfilled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" + "ID=" + ID + ", customerID=" + customerID + ", datePlaced=" + datePlaced + ", pickupTime=" + pickupTime + ", fulfilled=" + fulfilled + ", comment=" + comment + ", amount=" + amount + ", status=" + status + '}';
    }

}
