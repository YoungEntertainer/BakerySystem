/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.co.bakerysystem.jsp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import za.co.bakerysystem.dao.CategoryDAO;
import za.co.bakerysystem.dao.impl.CategoryDAOImpl;
import za.co.bakerysystem.model.Category;
import za.co.bakerysystem.service.CategoryService;
import za.co.bakerysystem.service.impl.CategoryServiceImpl;

/**
 *
 * @author user
 */
@MultipartConfig(maxRequestSize = 1024 * 1024 * 50)     // 50MB
public class CategoryServlet extends HttpServlet {
    
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final CategoryService categoryService = new CategoryServiceImpl(categoryDAO);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        
        List<Category> listOfCategory = categoryDAO.getAllCategory();
        
        int counter = 0;
        
        session.setAttribute("counter", counter);
        
        session.setAttribute("listOfCategory", listOfCategory);
        
        RequestDispatcher disp = request.getRequestDispatcher("category.jsp");
        disp.forward(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        // Get form parameters

        HttpSession session = request.getSession();
        
        String description = request.getParameter("description");
        byte[] picture = null;
        Part filePart = request.getPart("picture");
        if (filePart != null) {
            try (InputStream fileContent = filePart.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                picture = baos.toByteArray();
            }
        }
        String message = "fail";
        // Create Category object
        Category category = new Category();
        
        category.setDescription(description);
        category.setPicture(picture);

        // Call the service layer to add the category with image
        categoryService.addCategory(category);
        session.setAttribute("message", message);
        message = "success";
        session.setAttribute("message", message);
        
        request.getRequestDispatcher("addCategory.jsp").forward(request, resp);
        
    }
    
}
