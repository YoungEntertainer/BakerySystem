package za.co.bakerysystem.model;

public class PaymentType {

    private int ID;
    private String type;

    public PaymentType(int ID, String type) {
        this.ID = ID;
        this.type = type;
    }

    public PaymentType(String type) {
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
