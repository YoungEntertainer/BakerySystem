package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.SalesReportDetailsDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.SalesReportDetails;

public class SalesReportDetailsDAOImpl implements SalesReportDetailsDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;

    public SalesReportDetailsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public SalesReportDetailsDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public boolean createSaleDetail(SalesReportDetails salesReportDetails) {
        boolean creationSuccessful = false;
        connection = DbManager.getInstance().getConnection();

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO Sales_Report_Details (Sales_Report_ID, product_id, StartQuantity, QuantitySold, QuantityTrashed, PriceAtSale, FoodCostAtSale,profit,revenue,lost) VALUES(?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1, salesReportDetails.getSalesReportID());
            ps.setInt(2, salesReportDetails.getProductID());
            ps.setInt(3, salesReportDetails.getStartQuantity());
            ps.setInt(4, salesReportDetails.getQuantitySold());
            ps.setInt(5, salesReportDetails.getQuantityTrashed());
            ps.setDouble(6, salesReportDetails.getPriceAtSale());
            ps.setDouble(7, salesReportDetails.getFoodCostAtSale());
            ps.setDouble(8, salesReportDetails.getProfit());
            ps.setDouble(9, salesReportDetails.getRevenue());
            ps.setDouble(10, salesReportDetails.getLost());

            int affectedRows = ps.executeUpdate();
            creationSuccessful = affectedRows > 0;
        } catch (SQLException e) {
            // Handle the exception if needed
            e.printStackTrace();
        }

        return creationSuccessful;
    }

    @Override
    public boolean updateSaleDetail(SalesReportDetails salesReportDetails) {
        boolean updateSuccessful = false;
        connection = DbManager.getInstance().getConnection();

        try {
            ps = connection.prepareStatement(
                    "UPDATE Sales_Report_Details SET StartQuantity=?, QuantitySold=?, QuantityTrashed=?, PriceAtSale=?, FoodCostAtSale=? WHERE Sales_Report_ID=? AND product_id=?");

            ps.setInt(1, salesReportDetails.getStartQuantity());
            ps.setInt(2, salesReportDetails.getQuantitySold());
            ps.setInt(3, salesReportDetails.getQuantityTrashed());
            ps.setDouble(4, salesReportDetails.getPriceAtSale());
            ps.setDouble(5, salesReportDetails.getFoodCostAtSale());
            ps.setInt(6, salesReportDetails.getSalesReportID());
            ps.setInt(7, salesReportDetails.getProductID());

            int affectedRows = ps.executeUpdate();
            updateSuccessful = affectedRows > 0;
        } catch (SQLException e) {
            // Handle the exception if needed
            e.printStackTrace();
        }

        return updateSuccessful;
    }

    @Override
    public List<SalesReportDetails> getSalesDetails(int saleID) {
        List<SalesReportDetails> salesDetails = new ArrayList<>();
        connection = DbManager.getInstance().getConnection();

        try {
            ps = connection.prepareStatement(
                    "SELECT * FROM Sales_Report_Details WHERE Sales_Report_ID=?");

            ps.setInt(1, saleID);

            rs = ps.executeQuery();
            while (rs.next()) {
                SalesReportDetails salesReportDetails = new SalesReportDetails(
                        rs.getInt("Sales_Report_ID"),
                        rs.getInt("product_id"),
                        rs.getInt("StartQuantity"),
                        rs.getInt("QuantitySold"),
                        rs.getInt("QuantityTrashed"),
                        rs.getDouble("PriceAtSale"),
                        rs.getDouble("FoodCostAtSale"),
                        rs.getDouble("profit"),
                        rs.getDouble("revenue"),
                        rs.getDouble("lost")
                );
                salesDetails.add(salesReportDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesDetails;
    }

    @Override
    public boolean deleteSaleDetail(int reportID) {
        connection = DbManager.getInstance().getConnection();

        try {
            ps = connection.prepareStatement(
                    "DELETE FROM Sales_Report_Details WHERE Sales_Report_ID=?");

            ps.setInt(1, reportID);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Create an instance of the DAO
        SalesReportDetailsDAO salesReportDetailsDAO = new SalesReportDetailsDAOImpl();
//
//        // Create a sample SalesReportDetails object for testing
//        SalesReportDetails salesReportDetails = new SalesReportDetails();
//        salesReportDetails.setSalesReportID(1);
//        salesReportDetails.setProductID(2);
//        salesReportDetails.setStartQuantity(50);
//        salesReportDetails.setQuantitySold(20);
//        salesReportDetails.setQuantityTrashed(5);
//        salesReportDetails.setPriceAtSale(10.99);
//        salesReportDetails.setFoodCostAtSale(5.99);

        // Test createSaleDetail method
        //boolean creationResult = salesReportDetailsDAO.createSaleDetail(salesReportDetails);
        //System.out.println("Creation Result: " + creationResult);
        // Test updateSaleDetail method
//        salesReportDetails.setQuantitySold(25);
//        boolean updateResult = salesReportDetailsDAO.updateSaleDetail(salesReportDetails);
//        System.out.println("Update Result: " + updateResult);
        // Test getSalesDetails method
        int saleID = 1;
        List<SalesReportDetails> salesDetailsList = salesReportDetailsDAO.getSalesDetails(saleID);
        System.out.println("Sales Details for Sale ID " + saleID + ":");
        for (SalesReportDetails detail : salesDetailsList) {
            System.out.println(detail);
        }

        // Test deleteSaleDetail method
//        int reportIDToDelete = 1; // Replace with the actual reportID to delete
//        boolean deleteResult = salesReportDetailsDAO.deleteSaleDetail(reportIDToDelete);
//        System.out.println("Delete Result: " + deleteResult);
    }
}
