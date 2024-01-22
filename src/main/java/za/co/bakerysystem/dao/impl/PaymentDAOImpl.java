
package za.co.bakerysystem.dao.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.PaymentDAO;
import za.co.bakerysystem.model.Payment;
import za.co.bakerysystem.model.PaymentType;

public class PaymentDAOImpl implements PaymentDAO {

    // You need to provide the database connection details here
    private static final String JDBC_URL = "your_jdbc_url";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public void createPayment(Payment payment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Payment(order_id, Payment_Type_ID, Amount) VALUES (?, ?, ?)")) {

            statement.setInt(1, payment.getOrderID());
            statement.setInt(2, payment.getPaymentTypeID());
            statement.setDouble(3, payment.getAmount());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePayment(int orderID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Payment WHERE order_id = ?")) {

            statement.setInt(1, orderID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Payment> getOrderPayments(int orderID) {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Payment WHERE order_id = ?")) {

            statement.setInt(1, orderID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Payment payment = new Payment();
                    payment.setOrderID(resultSet.getInt("order_id"));
                    payment.setPaymentTypeID(resultSet.getInt("Payment_Type_ID"));
                    payment.setAmount(resultSet.getDouble("Amount"));
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public List<PaymentType> getPaymentTypes() {
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
}

