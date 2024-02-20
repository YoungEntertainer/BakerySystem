package za.co.bakerysystem.model;

import java.util.Base64;

public class Category {

    private int categoryId;
    private byte[] picture;
    private String description;

    public Category(int categoryId, String description) {
        this.categoryId = categoryId;
        this.description = description;
    }

    public Category(int categoryId, byte[] picture, String description) {
        this.categoryId = categoryId;
        this.picture = picture;
        this.description = description;
    }

    public Category() {
    }

    public Category(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureAsBase64() {
        if (picture != null) {
            return Base64.getEncoder().encodeToString(picture);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Category{"
                + "categoryId=" + categoryId
                + ", description='" + description + '\''
                + '}';
    }
}
