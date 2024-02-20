package za.co.bakerysystem.model;

public class Ingredient {

    private int ID;
    private String name;
    private double pricePerKG;
    private String note;
    private int quantity;
    private int UnitID;

    public Ingredient(int ID, String name, double pricePerKG, String note, int quantity, int UnitID) {
        this.ID = ID;
        this.name = name;
        this.pricePerKG = pricePerKG;
        this.note = note;
        this.quantity = quantity;
        this.UnitID = UnitID;
    }

    public Ingredient(String name, double pricePerKG, String note, int quantity, int UnitID) {
        this.name = name;
        this.pricePerKG = pricePerKG;
        this.note = note;
        this.quantity = quantity;
        this.UnitID = UnitID;
    }

    public Ingredient() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerKG() {
        return pricePerKG;
    }

    public void setPricePerKG(double pricePerKG) {
        this.pricePerKG = pricePerKG;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int UnitID) {
        this.UnitID = UnitID;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "ID=" + ID + ", name=" + name + ", pricePerKG=" + pricePerKG + ", note=" + note + ", quantity=" + quantity + ", UnitID=" + UnitID + '}';
    }

}
