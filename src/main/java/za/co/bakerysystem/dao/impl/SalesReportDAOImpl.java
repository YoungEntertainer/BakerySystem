package za.co.bakerysystem.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.SalesReportDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.SalesReport;
import za.co.bakerysystem.model.SalesReportDetails;

public class SalesReportDAOImpl implements SalesReportDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;
    private CallableStatement cs;

    public SalesReportDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public SalesReportDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public int getSaleQuantity(int productID) {
        int saleQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("CALL fetch_product_quantity_sale(?)");
            ps.setInt(1, productID);
            rs = ps.executeQuery();

            if (rs.next()) {
                saleQuantity = rs.getInt("saleQuantity");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return saleQuantity;
    }

    @Override
    public boolean createSale(SalesReport salesReport) {
        boolean creationSuccessful = false;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO Sales_Report (DatePlaced, Hours, Comment) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setObject(1, LocalDate.now());
            ps.setObject(2, salesReport.getHours());
            ps.setString(3, salesReport.getComment());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Sale created successfully, you can retrieve the generated ID
                    int generatedID = generatedKeys.getInt(1);
                    creationSuccessful = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creationSuccessful;
    }

    @Override
    public boolean updateSale(SalesReport salesReport) {
        boolean updateSuccessful = false;
        connection = DbManager.getInstance().getConnection();

        try {
            ps = connection.prepareStatement(
                    "UPDATE Sales_Report SET DatePlaced=?, Hours=?, Comment=? WHERE ID=?");

            ps.setObject(1, salesReport.getDate());
            ps.setObject(2, salesReport.getHours());
            ps.setString(3, salesReport.getComment());
            ps.setInt(4, salesReport.getID());

            int affectedRows = ps.executeUpdate();
            updateSuccessful = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccessful;
    }

    @Override
    public List<SalesReport> getSales(LocalDate startDate, LocalDate endDate) {
        List<SalesReport> salesReports = new ArrayList<>();
        connection = DbManager.getInstance().getConnection();

        try {
            cs = connection.prepareCall("{CALL fetch_all_sales_in_range(?, ?)}");
            cs.setObject(1, startDate);
            cs.setObject(2, endDate);

            rs = cs.executeQuery();
            while (rs.next()) {
                SalesReport salesReport = new SalesReport();
                salesReport.setID(rs.getInt("ID"));
                salesReport.setDate(rs.getObject("DatePlaced", LocalDate.class));
                salesReport.setHours(rs.getInt("Hours"));
                salesReport.setComment(rs.getString("Comment"));
                salesReports.add(salesReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReports;
    }

    @Override
    public List<SalesReport> getSalesInRange(LocalDate startDate, LocalDate endDate) {
        List<SalesReport> salesReports = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            cs = connection.prepareCall("{CALL fetch_sales_in_range(?, ?)}");
            cs.setObject(1, startDate);
            cs.setObject(2, endDate);

            rs = cs.executeQuery();
            while (rs.next()) {
                SalesReport salesReport = new SalesReport();
                salesReport.setID(rs.getInt("report_id"));
                salesReport.setDate(rs.getObject("DatePlaced", LocalDate.class));
                salesReport.setHours(rs.getInt("Hours"));
                salesReport.setComment(rs.getString("Comment"));
                salesReports.add(salesReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReports;
    }

    @Override
    public SalesReport getSale(int saleID) {
        SalesReport salesReport = new SalesReport();
        connection = DbManager.getInstance().getConnection();

        try {
            cs = connection.prepareCall("{CALL fetch_single_sale(?)}");
            cs.setInt(1, saleID);

            rs = cs.executeQuery();
            if (rs.next()) {
                salesReport.setID(rs.getInt("report_id"));
                salesReport.setDate(rs.getObject("DatePlaced", LocalDate.class));
                salesReport.setHours(rs.getInt("Hours"));
                salesReport.setComment(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return salesReport;
    }

    @Override
    public List<SalesReport> getSalesLast14Days() {
        List<SalesReport> salesReports = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            cs = connection.prepareCall("{CALL fetch_sales_two_weeks()}");

            rs = cs.executeQuery();
            while (rs.next()) {
                SalesReport salesReport = new SalesReport();
                salesReport.setID(rs.getInt("report_id"));
                salesReport.setDate(rs.getObject("DatePlaced", LocalDate.class));
                salesReport.setHours(rs.getInt("Hours"));
                salesReport.setComment(rs.getString("Comment"));
                salesReports.add(salesReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReports;
    }

    @Override
    public int getTotalSalesQuantity() {
        int totalSalesQuantity = 0;
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            String sql = "SELECT SUM(QuantitySold) as salesQuantity FROM Sales_Report_Details";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            if (rs.next()) {
                totalSalesQuantity = rs.getInt("salesQuantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSalesQuantity;
    }

    @Override
    public SalesReport getSaleNoProfit(int saleID) {
        SalesReport salesReport = new SalesReport();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("SELECT * FROM Sales_Report WHERE ID=?");
            ps.setInt(1, saleID);

            rs = ps.executeQuery();
            if (rs.next()) {
                salesReport.setID(rs.getInt("ID"));
                salesReport.setDate(rs.getObject("DatePlaced", LocalDate.class));
                salesReport.setHours(rs.getInt("Hours"));
                salesReport.setComment(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReport;
    }

    @Override
    public List<SalesReportDetails> getSalesDetails(int saleID) {
        List<SalesReportDetails> salesReports = new ArrayList<>();
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            cs = connection.prepareCall("{CALL fetch_single_sale_details(?)}");
            cs.setInt(1, saleID);

            rs = cs.executeQuery();

            while (rs.next()) {
                SalesReportDetails salesReportDetails = new SalesReportDetails();
                salesReportDetails.setSalesReportID(saleID);
                salesReportDetails.setProductID(rs.getInt("product_id"));
                salesReportDetails.setPriceAtSale(rs.getDouble("PriceAtSale"));
                salesReportDetails.setStartQuantity(rs.getInt("StartQuantity"));
                salesReportDetails.setQuantitySold(rs.getInt("QuantitySold"));
                salesReportDetails.setQuantityTrashed(rs.getInt("QuantityTrashed"));
                salesReportDetails.setFoodCostAtSale(rs.getDouble("FoodCostAtSale"));
                salesReportDetails.setProfit(rs.getDouble("profit"));
                salesReportDetails.setRevenue(rs.getDouble("revenue"));
                salesReportDetails.setLost(rs.getDouble("lost"));
                salesReports.add(salesReportDetails);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReports;
    }

    @Override
    public boolean deleteSalesDetail(int reportID) {
        db = DbManager.getInstance();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("DELETE FROM Sales_Report_Details WHERE Sales_Report_ID = ?");
            ps.setInt(1, reportID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSales(int saleID) {
        db = DbManager.getInstance();
        connection = db.getConnection();
        try {

            // Prepare the SQL query with a placeholder
            String query = "DELETE FROM Sales_Report WHERE ID = ?";
            ps = connection.prepareStatement(query);

            // Set value for the placeholder
            ps.setInt(1, saleID);

            // Execute the update
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        SalesReportDAO salesReportDAO = new SalesReportDAOImpl();

//        // Test createSale
        //  SalesReport newSale = new SalesReport();
//        newSale.setDate(LocalDate.now());
//        newSale.setHours(5);
//        newSale.setComment("Test sale comment");
//
//        boolean creationResult = salesReportDAO.createSale(newSale);
//        System.out.println("Sale creation result: " + creationResult);
        // Test updateSale
//        SalesReport saleToUpdate = salesReportDAO.getSale(1);
//        if (saleToUpdate != null) {
//            saleToUpdate.setComment("Updated comment");
//            boolean updateResult = salesReportDAO.updateSale(saleToUpdate);
//            System.out.println("Sale update result: " + updateResult);
//        } else {
//            System.out.println("Sale not found for update.");
//        }
        // Test getSales
//        LocalDate startDate = LocalDate.now().minusDays(7);
//        LocalDate endDate = LocalDate.now().plusDays(7);
//        List<SalesReport> salesInRange = salesReportDAO.getSales(startDate, endDate);
//        System.out.println("Sales within the date range:");
//        for (SalesReport sale : salesInRange) {
//            System.out.println(sale);
//        }
        //Test getSalesInRange
//        List<SalesReport> allSales = salesReportDAO.getSalesInRange(startDate, endDate);
//        System.out.println("All sales within the date range:");
//        for (SalesReport sale : allSales) {
//            System.out.println(sale);       //did not get any output
//        }
//        // Test getSale
//        int saleIDToFetch = 1;
//        SalesReport fetchedSale = salesReportDAO.getSale(saleIDToFetch);
//        System.out.println("Fetched sale by ID " + saleIDToFetch + ": " + fetchedSale);
//        // Test getSalesLast14Days
//        List<SalesReport> salesLast14Days = salesReportDAO.getSalesLast14Days();
//        System.out.println("Sales in the last 14 days:");
//        for (SalesReport sale : salesLast14Days) {
//            System.out.println(sale);
//        }
//
        // Test getTotalSalesQuantity
//        int totalSalesQuantity = salesReportDAO.getTotalSalesQuantity();
//        System.out.println("Total sales quantity: " + totalSalesQuantity);
        // Test getSaleNoProfit
//        SalesReport saleNoProfit = salesReportDAO.getSaleNoProfit(1);
//        System.out.println("Sale with no profit by ID " + 1 + ": " + saleNoProfit);
        // Test getSalesDetails
//        List<SalesReportDetails> salesDetails = salesReportDAO.getSalesDetails(1);
//        System.out.println("Sales details for sale ID " + 1 + ":");
//        for (SalesReportDetails sale : salesDetails) {
//            System.out.println(sale);
//        }
//        // Test deleteSalesDetail
//        int reportIDToDelete = 1; // Replace with an existing Sales_Report_Details ID
//        boolean deleteDetailResult = salesReportDAO.deleteSalesDetail(reportIDToDelete);
//        System.out.println("Delete Sales_Report_Details result: " + deleteDetailResult);
//
//        // Test deleteSales
//        boolean deleteSaleResult = salesReportDAO.deleteSales(saleIDToFetch);
//        System.out.println("Delete sale result: " + deleteSaleResult);
    }

}
