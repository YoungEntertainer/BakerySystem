package za.co.bakerysystem.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.SalesReportDAO;
import za.co.bakerysystem.model.SalesReport;

public class SalesReportDAOImpl implements SalesReportDAO {

    private static final String JDBC_URL = "your_jdbc_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public int createSale(SalesReport salesReport) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO Sales_Report (Date, Hours, Comment) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, salesReport.getDate());
            statement.setObject(2, salesReport.getHours());
            statement.setString(3, salesReport.getComment());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateSale(SalesReport salesReport) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE Sales_Report SET Date=?, Hours=?, Comment=? WHERE ID=?")) {

            statement.setObject(1, salesReport.getDate());
            statement.setObject(2, salesReport.getHours());
            statement.setString(3, salesReport.getComment());
            statement.setInt(4, salesReport.getID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SalesReport> getSales(LocalDate startDate, LocalDate endDate) {
        List<SalesReport> salesReports = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement statement = connection.prepareCall("{CALL fetch_all_sales_in_range(?, ?)}")) {

            statement.setObject(1, startDate);
            statement.setObject(2, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesReport salesReport = new SalesReport();
                    salesReport.setID(resultSet.getInt("ID"));
                    salesReport.setDate(resultSet.getObject("Date", LocalDate.class));
                    salesReport.setHours(resultSet.getInt("Hours"));
                    salesReport.setComment(resultSet.getString("Comment"));
                    salesReports.add(salesReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReports;
    }

    @Override
    public List<SalesReport> getSalesInRange(LocalDate startDate, LocalDate endDate) {
        List<SalesReport> salesReports = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement statement = connection.prepareCall("{CALL fetch_sales_in_range(?, ?)}")) {

            statement.setObject(1, startDate);
            statement.setObject(2, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesReport salesReport = new SalesReport();
                    salesReport.setID(resultSet.getInt("ID"));
                    salesReport.setDate(resultSet.getObject("Date", LocalDate.class));
                    salesReport.setHours(resultSet.getInt("Hours"));
                    salesReport.setComment(resultSet.getString("Comment"));
                    salesReports.add(salesReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReports;
    }

    @Override
    public SalesReport getSale(int saleID) {
        SalesReport salesReport = new SalesReport();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement statement = connection.prepareCall("{CALL fetch_single_sale(?)}")) {

            statement.setInt(1, saleID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    salesReport.setID(resultSet.getInt("ID"));
                    salesReport.setDate(resultSet.getObject("Date", LocalDate.class));
                    salesReport.setHours(resultSet.getInt("Hours"));
                    salesReport.setComment(resultSet.getString("Comment"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReport;
    }

    @Override
    public List<SalesReport> getSalesLast14Days() {
        List<SalesReport> salesReports = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement statement = connection.prepareCall("{CALL fetch_sales_two_weeks()}")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesReport salesReport = new SalesReport();
                    salesReport.setID(resultSet.getInt("ID"));
                    salesReport.setDate(resultSet.getObject("Date", LocalDate.class));
                    salesReport.setHours(resultSet.getInt("Hours"));
                    salesReport.setComment(resultSet.getString("Comment"));
                    salesReports.add(salesReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReports;
    }

    @Override
    public int getTotalSalesQuantity() {
        int totalSalesQuantity = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT SUM(QuantitySold) as salesQuantity FROM Sales_Report_Details")) {

            if (resultSet.next()) {
                totalSalesQuantity = resultSet.getInt("salesQuantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSalesQuantity;
    }

    @Override
    public SalesReport getSaleNoProfit(int saleID) {
        SalesReport salesReport = new SalesReport();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Sales_Report WHERE ID=?")) {

            statement.setInt(1, saleID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    salesReport.setID(resultSet.getInt("ID"));
                    salesReport.setDate(resultSet.getObject("Date", LocalDate.class));
                    salesReport.setHours(resultSet.getInt("Hours"));
                    salesReport.setComment(resultSet.getString("Comment"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReport;
    }

    @Override
    public List<SalesReport> getSalesDetails(int saleID) {
        List<SalesReport> salesReports = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                CallableStatement statement = connection.prepareCall("{CALL fetch_single_sale_details(?)}")) {

            statement.setInt(1, saleID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesReport salesReport = new SalesReport();
                    salesReport.setID(resultSet.getInt("ID"));
                    salesReport.setDate(resultSet.getObject("Date", LocalDate.class));
                    salesReport.setHours(resultSet.getInt("Hours"));
                    salesReport.setComment(resultSet.getString("Comment"));
                    salesReports.add(salesReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReports;
    }

    @Override
    public void deleteSalesDetail(int reportID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM Sales_Report_Details WHERE Sales_Report_ID = ?")) {

            statement.setInt(1, reportID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSales(List<Integer> salesIDs) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();

            StringBuilder saleIDString = new StringBuilder();
            for (Integer saleID : salesIDs) {
                if (saleIDString.length() > 0) {
                    saleIDString.append(",");
                }
                saleIDString.append(saleID);
            }

            String query = "DELETE FROM Sales_Report WHERE ID IN (" + saleIDString.toString() + ")";
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
