package za.co.bakerysystem.service.impl;

import za.co.bakerysystem.dao.AdminDAO;
import za.co.bakerysystem.dao.impl.AdminDAOImpl;
import za.co.bakerysystem.model.Admin;
import za.co.bakerysystem.service.AdminService;

public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAOImpl();
    }

    @Override
    public Admin getAdminById(int adminID) {
        return adminDAO.getAdminById(adminID);

    }

    @Override
    public Admin login(String emailAddress, String password) {
        return adminDAO.login(emailAddress, password);
    }

    @Override
    public boolean createAdmin(Admin admin) {
        return adminDAO.createAdmin(admin);
    }
    
     public boolean exists(int id) {

        return false;
    }

    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        AdminService adminService = new AdminServiceImpl();

//        // Testing createAdmin
//        Admin newAdmin = new Admin("newadmin@example.com", "newpassword"); // Create a new Admin object
//        boolean adminCreated = adminService.createAdmin(newAdmin);
//        if (adminCreated) {
//            System.out.println("New admin created successfully: " + newAdmin);
//        } else {
//            System.out.println("Failed to create new admin.");
//        }
        // Testing getAdminById
//        int adminIdToRetrieve = 1; // Change this to the actual admin ID you want to retrieve
//        Admin retrievedAdmin = adminService.getAdminById(adminIdToRetrieve);
//        if (retrievedAdmin != null) {
//            System.out.println("Admin retrieved successfully: " + retrievedAdmin);
//        } else {
//            System.out.println("Admin with ID " + adminIdToRetrieve + " not found.");
//        }
//
        // Testing login
        //  String email = "newadmin@example.com"; // Change this to the actual email you want to use for login
//        String password = "newpassword"; // Change this to the actual password you want to use for login
//        Admin loggedInAdmin = adminService.login(email, password);
//        if (loggedInAdmin != null) {
//            System.out.println("Admin logged in successfully: " + loggedInAdmin);
//        } else {
//            System.out.println("Login failed. Incorrect email or password.");
//        }
    }

}
