package za.co.bakerysystem.model;

public class Admin {

    private int adminID;
    private String emailAddress;
    private String password;

    public Admin(int adminID, String emailAddress, String password) {
        this.adminID = adminID;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public Admin() {
    }

    public Admin(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdminID() {
        return adminID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Administrator{"
                + "adminID=" + adminID
                + ", emailAddress='" + emailAddress + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
