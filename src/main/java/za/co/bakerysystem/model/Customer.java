
package za.co.bakerysystem.model;

import java.time.LocalDateTime;

public class Customer {

    private int ID;
    private String customerName;
    private String customerIDNo;
    private String phoneNumber;
    private LocalDateTime joinDate;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String zip;
    private String comment;
    private String email;
    private String password;

    public Customer(int ID, String customerName, String customerIDNo, String phoneNumber, LocalDateTime joinDate, String addressOne, String addressTwo, String city, String zip, String comment, String email, String password) {
        this.ID = ID;
        this.customerName = customerName;
        this.customerIDNo = customerIDNo;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.city = city;
        this.zip = zip;
        this.comment = comment;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIDNo() {
        return customerIDNo;
    }

    public void setCustomerIDNo(String customerIDNo) {
        this.customerIDNo = customerIDNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Customer{" + "ID=" + ID + ", customerName=" + customerName + ", customerIDNo=" + customerIDNo + ", phoneNumber=" + phoneNumber + ", joinDate=" + joinDate + ", addressOne=" + addressOne + ", addressTwo=" + addressTwo + ", city=" + city + ", zip=" + zip + ", comment=" + comment + '}';
    }

    
}
