package za.co.bakerysystem.dao.impl;

import za.co.bakerysystem.dao.OrderProfitDAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProfitDAOImpl implements OrderProfitDAO {

    private static final String JDBC_URL = "jdbc:your_database_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

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

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(query)) {

            setParameters(statement, params);

            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        row.put(columnName, value);
                    }
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
