package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.SalesReportDetailsDAO;
import za.co.bakerysystem.model.SalesReportDetails;

public class SalesReportDetailsDAOImpl implements SalesReportDetailsDAO {

    private static final String JDBC_URL = "your_jdbc_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public void createSaleDetail(SalesReportDetails salesReportDetails) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO Sales_Report_Details (Sales_Report_ID, product_id, StartQuantity, QuantitySold, QuantityTrashed, PriceAtSale, FoodCostAtSale) VALUES(?,?,?,?,?,?,?)")) {

            statement.setInt(1, salesReportDetails.getSalesReportID());
            statement.setInt(2, salesReportDetails.getProductID());
            statement.setInt(3, salesReportDetails.getStartQuantity());
            statement.setInt(4, salesReportDetails.getQuantitySold());
            statement.setInt(5, salesReportDetails.getQuantityTrashed());
            statement.setBigDecimal(6, salesReportDetails.getPriceAtSale());
            statement.setBigDecimal(7, salesReportDetails.getFoodCostAtSale());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSaleDetail(SalesReportDetails salesReportDetails) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE Sales_Report_Details SET StartQuantity=?, QuantitySold=?, QuantityTrashed=?, PriceAtSale=?, FoodCostAtSale=? WHERE Sales_Report_ID=? AND product_id=?")) {

            statement.setInt(1, salesReportDetails.getStartQuantity());
            statement.setInt(2, salesReportDetails.getQuantitySold());
            statement.setInt(3, salesReportDetails.getQuantityTrashed());
            statement.setBigDecimal(4, salesReportDetails.getPriceAtSale());
            statement.setBigDecimal(5, salesReportDetails.getFoodCostAtSale());
            statement.setInt(6, salesReportDetails.getSalesReportID());
            statement.setInt(7, salesReportDetails.getProductID());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SalesReportDetails> getSalesDetails(int saleID) {
        List<SalesReportDetails> salesDetails = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM Sales_Report_Details WHERE Sales_Report_ID=?")) {

            statement.setInt(1, saleID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesReportDetails salesReportDetails = new SalesReportDetails(
                            resultSet.getInt("Sales_Report_ID"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("StartQuantity"),
                            resultSet.getInt("QuantitySold"),
                            resultSet.getInt("QuantityTrashed"),
                            resultSet.getBigDecimal("PriceAtSale"),
                            resultSet.getBigDecimal("FoodCostAtSale")
                    );
                    salesDetails.add(salesReportDetails);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesDetails;
    }

    @Override
    public void deleteSaleDetail(int reportID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM Sales_Report_Details WHERE Sales_Report_ID=?")) {

            statement.setInt(1, reportID);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
