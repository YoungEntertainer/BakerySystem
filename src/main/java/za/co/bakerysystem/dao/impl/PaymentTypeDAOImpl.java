package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.PaymentTypeDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.PaymentType;

public class PaymentTypeDAOImpl implements PaymentTypeDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;

    public PaymentTypeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public PaymentTypeDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    @Override
    public boolean create(PaymentType paymentType) {
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("INSERT INTO Payment_Type (Type) VALUES (?)");

            ps.setString(1, paymentType.getType());
            int affectedRows = ps.executeUpdate();

            // Check if the insertion was successful
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public PaymentType getById(int id) {
        PaymentType paymentType = null;
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("SELECT * FROM Payment_Type WHERE Payment_Type_ID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                paymentType = new PaymentType();
                paymentType.setID(rs.getInt("Payment_Type_ID"));
                paymentType.setType(rs.getString("Type"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return paymentType;
    }

    @Override
    public List<PaymentType> getAll() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("SELECT * FROM Payment_Type");
            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentType paymentType = new PaymentType();
                paymentType.setID(rs.getInt("Payment_Type_ID"));
                paymentType.setType(rs.getString("Type"));
                paymentTypes.add(paymentType);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return paymentTypes;
    }

    @Override
    public boolean update(PaymentType paymentType) {
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("UPDATE Payment_Type SET Type = ? WHERE Payment_Type_ID = ?");

            ps.setString(1, paymentType.getType());
            ps.setInt(2, paymentType.getID());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        connection = db.getConnection();

        try {
            ps = connection.prepareStatement("DELETE FROM Payment_Type WHERE Payment_Type_ID = ?");

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }

        return false;
    }

    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Create an instance of PaymentTypeDAO
        PaymentTypeDAO paymentTypeDAO = new PaymentTypeDAOImpl();

        // Test the save method
//        PaymentType newPaymentType = new PaymentType();
//        newPaymentType.setType("Cashhhhhhhh");
////
//        boolean saveSuccess = paymentTypeDAO.create(newPaymentType);
//        System.out.println("Save success: " + saveSuccess);
//        // Test the findAll method
//        List<PaymentType> paymentTypes = paymentTypeDAO.getAll();
//        System.out.println("All Payment Types: " + paymentTypes);
        // Test the findById method
//        PaymentType foundPaymentType = paymentTypeDAO.getById(2); // Assuming ID 1 exists
//        System.out.println("Found Payment Type: " + foundPaymentType);
//
//        // Test the update method
//        if (foundPaymentType != null) {
//            foundPaymentType.setType("Credit Card");
//            boolean updateSuccess = paymentTypeDAO.update(foundPaymentType);
//            System.out.println("Update success: " + updateSuccess);
//        }
//        // Test the delete method
//        boolean deleteSuccess = paymentTypeDAO.delete(1); // Assuming ID 1 exists
//        System.out.println("Delete success: " + deleteSuccess);
    }

}
