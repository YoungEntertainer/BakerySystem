package za.co.bakerysystem.dao.impl;

import za.co.bakerysystem.dao.OrderProfitDAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import za.co.bakerysystem.dbmanager.DbManager;

public class OrderProfitDAOImpl implements OrderProfitDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;

    public OrderProfitDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public OrderProfitDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public List<Map<String, Object>> fetchOrderProfit() {
        return executeQuery("CALL fetch_order_profit()");
    }

    @Override
    public List<Map<String, Object>> fetchOrderProfitLastMonth() {
        return executeQuery("CALL fetch_order_profit_lastMonth()");
    }

    @Override
    public List<Map<String, Object>> fetchSaleProfit() {
        return executeQuery("CALL fetch_sale_profit()");
    }

    @Override
    public List<Map<String, Object>> fetchSaleProfitLastMonth() {
        return executeQuery("CALL fetch_sale_profit_lastMonth()");
    }

    @Override
    public List<Map<String, Object>> fetchOrderProfitInRange(LocalDate startDate, LocalDate endDate) {
        return executeQuery("CALL fetch_order_profit_in_range(?, ?)", startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> fetchSaleProfitInRange(LocalDate startDate, LocalDate endDate) {
        return executeQuery("CALL fetch_sale_profit_in_range(?, ?)", startDate, endDate);
    }

    private List<Map<String, Object>> executeQuery(String query, Object... params) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(query);

            setParameters(ps, params);

            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                resultList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(query);

            setParameters(ps, params);

            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                resultList.add(row);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }

        return resultList;
    }

    private void setParameters(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }

    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        OrderProfitDAO orderProfitDAO = new OrderProfitDAOImpl();

        // Test fetchOrderProfit
//        System.out.println("Order Profit:");
//        List<Map<String, Object>> orderProfitList = orderProfitDAO.fetchOrderProfit();
//        printResult(orderProfitList);
//        // Test fetchOrderProfitLastMonth
//        System.out.println("\nOrder Profit Last Month:");
//        List<Map<String, Object>> orderProfitLastMonthList = orderProfitDAO.fetchOrderProfitLastMonth();
//        printResult(orderProfitLastMonthList);
//        // Test fetchSaleProfit
//        System.out.println("\nSale Profit:");
//        List<Map<String, Object>> saleProfitList = orderProfitDAO.fetchSaleProfit();
//        printResult(saleProfitList);
//        // Test fetchSaleProfitLastMonth
//        System.out.println("\nSale Profit Last Month:");
//        List<Map<String, Object>> saleProfitLastMonthList = orderProfitDAO.fetchSaleProfitLastMonth();
//        printResult(saleProfitLastMonthList);
        // Test fetchOrderProfitInRange
        //       System.out.println("\nOrder Profit in Range:");
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
//        List<Map<String, Object>> orderProfitInRangeList = orderProfitDAO.fetchOrderProfitInRange(startDate, endDate);
//        printResult(orderProfitInRangeList);

        // Test fetchSaleProfitInRange
//        System.out.println("\nSale Profit in Range:");
//        List<Map<String, Object>> saleProfitInRangeList = orderProfitDAO.fetchSaleProfitInRange(startDate, endDate);
//        printResult(saleProfitInRangeList);
    }

    private static void printResult(List<Map<String, Object>> resultList) {
        for (Map<String, Object> row : resultList) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("----------");
        }
    }

}
