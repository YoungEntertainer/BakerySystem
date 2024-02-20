package za.co.bakerysystem.model;

public class SalesReportDetails {

    private int salesReportID;
    private int productID;
    private int startQuantity;
    private int quantitySold;
    private int quantityTrashed;
    private double priceAtSale;
    private double foodCostAtSale;
    private double profit;
    private double revenue;
    private double lost;

    public SalesReportDetails(int salesReportID, int productID, int startQuantity, int quantitySold, int quantityTrashed, double priceAtSale, double foodCostAtSale, double profit, double revenue, double lost) {
        this.salesReportID = salesReportID;
        this.productID = productID;
        this.startQuantity = startQuantity;
        this.quantitySold = quantitySold;
        this.quantityTrashed = quantityTrashed;
        this.priceAtSale = priceAtSale;
        this.foodCostAtSale = foodCostAtSale;
        this.profit = profit;
        this.revenue = revenue;
        this.lost = lost;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getLost() {
        return lost;
    }

    public void setLost(double lost) {
        this.lost = lost;
    }

    public SalesReportDetails() {
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

    public int getStartQuantity() {
        return startQuantity;
    }

    public void setStartQuantity(int startQuantity) {
        this.startQuantity = startQuantity;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getQuantityTrashed() {
        return quantityTrashed;
    }

    public void setQuantityTrashed(int quantityTrashed) {
        this.quantityTrashed = quantityTrashed;
    }

    public double getPriceAtSale() {
        return priceAtSale;
    }

    public void setPriceAtSale(double priceAtSale) {
        this.priceAtSale = priceAtSale;
    }

    public double getFoodCostAtSale() {
        return foodCostAtSale;
    }

    public void setFoodCostAtSale(double foodCostAtSale) {
        this.foodCostAtSale = foodCostAtSale;
    }

    @Override
    public String toString() {
        return "SalesReportDetails{" + "salesReportID=" + salesReportID + ", productID=" + productID + ", startQuantity=" + startQuantity + ", quantitySold=" + quantitySold + ", quantityTrashed=" + quantityTrashed + ", priceAtSale=" + priceAtSale + ", foodCostAtSale=" + foodCostAtSale + ", profit=" + profit + ", revenue=" + revenue + ", lost=" + lost + '}';
    }

}
