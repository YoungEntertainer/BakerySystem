/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.co.bakerysystem.jsp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakerysystem.model.Category;
import za.co.bakerysystem.service.CategoryService;
import za.co.bakerysystem.service.impl.CategoryServiceImpl;

/**
 *
 * @author user
 */
public class HomeServlet extends HttpServlet {

     private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        // Initialize your service in the init method
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                List<Category> allCategories = categoryService.getAllCategory();

        // Set the result in request attributes or session attributes
        request.setAttribute("allCategories", allCategories);

        // Forward to your JSP page for rendering
        request.getRequestDispatcher("/home.jsp").forward(request, response);

    }

}
