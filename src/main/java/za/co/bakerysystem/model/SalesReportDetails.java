
package za.co.bakerysystem.model;

import java.math.BigDecimal;

public class SalesReportDetails {

    private int salesReportID;
    private int productID;
    private Integer startQuantity;
    private Integer quantitySold;
    private Integer quantityTrashed;
    private BigDecimal priceAtSale;
    private BigDecimal foodCostAtSale;

    public SalesReportDetails(int salesReportID, int productID, Integer startQuantity, Integer quantitySold, Integer quantityTrashed, BigDecimal priceAtSale, BigDecimal foodCostAtSale) {
        this.salesReportID = salesReportID;
        this.productID = productID;
        this.startQuantity = startQuantity;
        this.quantitySold = quantitySold;
        this.quantityTrashed = quantityTrashed;
        this.priceAtSale = priceAtSale;
        this.foodCostAtSale = foodCostAtSale;
    }

    public int getSalesReportID() {
        return salesReportID;
    }

    public void setSalesReportID(int salesReportID) {
        this.salesReportID = salesReportID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Integer getStartQuantity() {
        return startQuantity;
    }

    public void setStartQuantity(Integer startQuantity) {
        this.startQuantity = startQuantity;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Integer getQuantityTrashed() {
        return quantityTrashed;
    }

    public void setQuantityTrashed(Integer quantityTrashed) {
        this.quantityTrashed = quantityTrashed;
    }

    public BigDecimal getPriceAtSale() {
        return priceAtSale;
    }

    public void setPriceAtSale(BigDecimal priceAtSale) {
        this.priceAtSale = priceAtSale;
    }

    public BigDecimal getFoodCostAtSale() {
        return foodCostAtSale;
    }

    public void setFoodCostAtSale(BigDecimal foodCostAtSale) {
        this.foodCostAtSale = foodCostAtSale;
    }

    @Override
    public String toString() {
        return "SalesReportDetails{" + "salesReportID=" + salesReportID + ", productID=" + productID + ", startQuantity=" + startQuantity + ", quantitySold=" + quantitySold + ", quantityTrashed=" + quantityTrashed + ", priceAtSale=" + priceAtSale + ", foodCostAtSale=" + foodCostAtSale + '}';
    }

    
}
