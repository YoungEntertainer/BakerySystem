package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.co.bakerysystem.dao.AdminDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Admin;

public class AdminDAOImpl implements AdminDAO {

    private Connection connection;
    private static DbManager db;
    private PreparedStatement ps;
    private ResultSet rs;
    ResultSet generatedKeys;

    public AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public AdminDAOImpl() {
        db = DbManager.getInstance();
        this.connection = db.getConnection();
    }

    // SQL queries
    private static final String SELECT_ADMIN_BY_ID = "SELECT * FROM Admin WHERE adminID = ?";
    private static final String LOGIN_ADMIN = "SELECT * FROM Admin WHERE emailAddress = ? AND password = ?";
    private static final String INSERT_ADMIN = "INSERT INTO Admin (emailAddress, password) VALUES (?, ?)";

    @Override
    public Admin getAdminById(int adminID) {
        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(SELECT_ADMIN_BY_ID);
            ps.setInt(1, adminID);
            rs = ps.executeQuery();

            if (rs.next()) {
                return extractAdminFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createAdmin(Admin admin) {
        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(INSERT_ADMIN, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, admin.getEmailAddress());
            ps.setString(2, admin.getPassword());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int adminID = generatedKeys.getInt(1);
                    admin.setAdminID(adminID);
                    return true; // Admin added successfully
                } else {
                    throw new SQLException("Creating admin failed, no ID obtained.");
                }
            } else {
                return false; // Admin creation failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Admin creation failed
        }
    }

    @Override
    public Admin login(String emailAddress, String password) {
        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(LOGIN_ADMIN);
            ps.setString(1, emailAddress);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                return extractAdminFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        int adminID = rs.getInt("adminID");
        String emailAddress = rs.getString("emailAddress");
        String password = rs.getString("password");

        return new Admin(adminID, emailAddress, password);
    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        testAdminDAO();
    }

    private static void testAdminDAO() {
        AdminDAO adminDAO = new AdminDAOImpl();

        // Test createAdmin
//        Admin newAdmin = new Admin();
//        newAdmin.setEmailAddress("test@example.com");
//        newAdmin.setPassword("testPassword");
//
//        boolean adminCreated = adminDAO.createAdmin(newAdmin);
//
//        if (adminCreated) {
//            System.out.println("Admin created successfully with ID: " + newAdmin.getAdminID());
//        } else {
//            System.out.println("Failed to create admin.");
//        }
        // Test getAdminById
//        int adminIDToRetrieve = 1;
//        Admin retrievedAdmin = adminDAO.getAdminById(adminIDToRetrieve);
//
//        if (retrievedAdmin != null) {
//            System.out.println("Admin retrieved successfully: " + retrievedAdmin);
//        } else {
//            System.out.println("Failed to retrieve admin with ID: " + adminIDToRetrieve);
//        }
        // Test login
//        String loginEmail = "test@example.com";
//        String loginPassword = "testPassword";
//        Admin loggedInAdmin = adminDAO.login(loginEmail, loginPassword);
//
//        if (loggedInAdmin != null) {
//            System.out.println("Login successful. Admin details: " + loggedInAdmin);
//        } else {
//            System.out.println("Login failed for email: " + loginEmail);
//        }
    }

}
