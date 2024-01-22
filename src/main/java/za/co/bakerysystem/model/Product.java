package za.co.bakerysystem.model;

public class Product {

    private int ID;
    private String name;
    private double price;
    private double foodCost;
    private int timeCost;
    private String comment;
    private String Description;
    private String NutrientInformation;
    private String Warnings;
    private int CategoryID;

    public Product(int ID, String name, double price, double foodCost, int timeCost, String comment, String Description, String NutrientInformation, String Warnings, int CategoryID) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.foodCost = foodCost;
        this.timeCost = timeCost;
        this.comment = comment;
        this.Description = Description;
        this.NutrientInformation = NutrientInformation;
        this.Warnings = Warnings;
        this.CategoryID = CategoryID;
    }
    
    

    public Product(String name, double price, double foodCost, int timeCost, String comment, String Description, String NutrientInformation, String Warnings, int CategoryID) {
        this.name = name;
        this.price = price;
        this.foodCost = foodCost;
        this.timeCost = timeCost;
        this.comment = comment;
        this.Description = Description;
        this.NutrientInformation = NutrientInformation;
        this.Warnings = Warnings;
        this.CategoryID = CategoryID;
    }

    public Product() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getNutrientInformation() {
        return NutrientInformation;
    }

    public void setNutrientInformation(String NutrientInformation) {
        this.NutrientInformation = NutrientInformation;
    }

    public String getWarnings() {
        return Warnings;
    }

    public void setWarnings(String Warnings) {
        this.Warnings = Warnings;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(double foodCost) {
        this.foodCost = foodCost;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Product{" + "ID=" + ID + ", name=" + name + ", price=" + price + ", foodCost=" + foodCost + ", timeCost=" + timeCost + ", comment=" + comment + '}';
    }

}
