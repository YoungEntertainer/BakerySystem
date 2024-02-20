package za.co.bakerysystem.dao;

import za.co.bakerysystem.model.Admin;

public interface AdminDAO {

    Admin getAdminById(int adminID);

    boolean createAdmin(Admin admin);

    Admin login(String emailAddress, String password);
}
