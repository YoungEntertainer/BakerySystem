/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.bakerysystem.model;

public class PaymentType {

    private int ID;
    private String type;

    public PaymentType(int ID, String type) {
        this.ID = ID;
        this.type = type;
    }

    public PaymentType() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PaymentType{" + "ID=" + ID + ", type=" + type + '}';
    }

    
}
