
package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.PaymentTypeDAO;
import za.co.bakerysystem.model.PaymentType;

public class PaymentTypeDAOImpl implements PaymentTypeDAO {

    // You need to provide the database connection details here
    private static final String JDBC_URL = "your_jdbc_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public void save(PaymentType paymentType) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Payment_Type (Type) VALUES (?)")) {

            statement.setString(1, paymentType.getType());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PaymentType findById(int id) {
        PaymentType paymentType = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payment_Type WHERE ID = ?")) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    paymentType = new PaymentType();
                    paymentType.setID(resultSet.getInt("ID"));
                    paymentType.setType(resultSet.getString("Type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentType;
    }

    @Override
    public List<PaymentType> findAll() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payment_Type");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                PaymentType paymentType = new PaymentType();
                paymentType.setID(resultSet.getInt("ID"));
                paymentType.setType(resultSet.getString("Type"));
                paymentTypes.add(paymentType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentTypes;
    }

    @Override
    public void update(PaymentType paymentType) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Payment_Type SET Type = ? WHERE ID = ?")) {

            statement.setString(1, paymentType.getType());
            statement.setInt(2, paymentType.getID());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Payment_Type WHERE ID = ?")) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

