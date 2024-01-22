/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.bakerysystem.model;

public class Recipe {

    private int productID;
    private String comment;

    public Recipe(int productID, String comment) {
        this.productID = productID;
        this.comment = comment;
    }

    public Recipe() {
    }

    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Recipe{" + "productID=" + productID + ", comment=" + comment + '}';
    }

    
    
}