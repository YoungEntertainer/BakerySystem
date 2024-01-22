
package za.co.bakerysystem.dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    private static DbManager instance = null;
    private Connection con = null;

    private DbManager() {
        String url = "jdbc:mysql://localhost:3306/bakery?useSSL=false";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Got a connection");
        } catch (SQLException se) {
            se.printStackTrace();
            System.err.println("Could not connect: " + se.getMessage());
        } catch (ClassNotFoundException se) {
            se.printStackTrace();
            System.err.println("Could not connect: " + se.getMessage());
        }

    }

    //singleton
    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }

    public boolean closeConnection() {
        boolean retVal = false;
        if (con != null) {
            try {
                con.close();
                retVal = true;
            } catch (SQLException ex) {
                System.out.println("Error closing connection: " + ex.getMessage());
            } finally {
                con = null;
            }
        }
        return retVal;
    }
}

