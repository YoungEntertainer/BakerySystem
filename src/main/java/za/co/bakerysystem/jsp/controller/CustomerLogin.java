package za.co.bakerysystem.jsp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakerysystem.dao.CustomerDAO;
import za.co.bakerysystem.dao.impl.CustomerDAOImpl;

/**
 *
 * @author user
 */
@WebServlet(name = "CustomerLogin", urlPatterns = {"/CustomerLogin.do"})
public class CustomerLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAOImpl();

        //Getting all data from user/customer
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //Creating Session
        HttpSession hs = request.getSession();
        try {
            //Creating Resultset

            if (email != null && password != null) {
                if (customerDAO.login(email, password) != null) {
                    //Storing the login details in session
                    hs.setAttribute("id", customerDAO.login(email, password).getID());
                    hs.setAttribute("name", customerDAO.login(email, password).getCustomerName());
                    response.sendRedirect("index.jsp");

                } else {
                    //If wrong credentials are entered
                    String message = "You have enter wrong credentials";
                    hs.setAttribute("credential", message);
                    //Redirecting response to the customer-login.jsp
                    response.sendRedirect("customer-login.jsp");
                }

            } else {
                //If username or password is empty or null
                String message = "User name or Password is null";
                hs.setAttribute("credential", message);
                //Redirecting response to the customer-login.jsp
                response.sendRedirect("customer-login.jsp");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
