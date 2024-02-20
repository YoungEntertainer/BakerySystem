package za.co.bakerysystem.service;

import za.co.bakerysystem.model.Admin;

public interface AdminService {

    Admin getAdminById(int adminID);

    boolean createAdmin(Admin admin);

    Admin login(String emailAddress, String password);
}
