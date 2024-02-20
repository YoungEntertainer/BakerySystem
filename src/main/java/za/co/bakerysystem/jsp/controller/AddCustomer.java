/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import za.co.bakerysystem.model.Customer;

@WebServlet(name = "AddCustomer", urlPatterns = {"/AddCustomer.do"})
public class AddCustomer extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession hs = request.getSession();
        CustomerDAO customerDAO = new CustomerDAOImpl();
        
        String CustomerName = request.getParameter("customerName");
        String customerIDNo = request.getParameter("customerIDNo");
        String phoneNumber = request.getParameter("phoneNumber");
        String addressOne = request.getParameter("addressOne");
        String zip = request.getParameter("zip");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        

       Customer customer = new Customer(CustomerName, customerIDNo, phoneNumber, addressOne, city, zip, email, password);
//        Customer customer = new Customer(3, CustomerName, customerIDNo, phoneNumber, LocalDateTime.now(), addressOne, addressOne, city, zip, email, email, password);
            customer.setCustomerName(CustomerName);
        try{
        if (customerDAO.createCustomer(customer)) {
            String message = "Customer register successfully.";
            //Passing message via session.
            hs.setAttribute("success-message", message);
            //Sending response back to the user/customer
            request.getRequestDispatcher("customer-register.jsp").forward(request, response);

        } else {
            //If customer fails to register 
            String message = "Customer registration fail";
            //Passing message via session.
            hs.setAttribute("fail-message", message);
            //Sending response back to the user/customer
            request.getRequestDispatcher("customer-register.jsp").forward(request, response);

        }
        }catch(IOException | ServletException e)
        {
            System.out.println("Error:"+e.getMessage());
        }

    }

}
