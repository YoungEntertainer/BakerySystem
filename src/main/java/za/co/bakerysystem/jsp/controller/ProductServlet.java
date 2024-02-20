package za.co.bakerysystem.jsp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dao.impl.ProductDAOImpl;
import za.co.bakerysystem.model.Product;

@MultipartConfig(maxRequestSize = 1024 * 1024 * 50)     // 50MB
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve the categoryId parameter from the request
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        ProductDAO productDAO = new ProductDAOImpl();

        // Assuming you have a ProductService class to handle fetching products
//        ProductService productService = new ProductServiceImpl(productDAO);
        // Fetch the list of products based on the categoryId
        List<Product> productList = productDAO.getAllProductByCategory(categoryId);
        List<Product> products = productDAO.getProducts();

        session.setAttribute("products", products);

        // Store the productList in request scope for the JSP to access
        session.setAttribute("productList", productList);

        // Forward the request to the products.jsp page (or any other JSP page you want to display the products)
        request.getRequestDispatcher("/getProducts.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductDAO productDAO = new ProductDAOImpl();

        String name = request.getParameter("Name");
        Double price = Double.parseDouble(request.getParameter("Price"));
        Double foodCost = Double.parseDouble(request.getParameter("FoodCost"));
        int timeCost = Integer.parseInt(request.getParameter("TimeCost"));
        byte[] picture = null;
        Part filePart = request.getPart("Picture");
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
        String description = request.getParameter("Description");
        String nutrientInformation = request.getParameter("NutrientInformation");
        String warnings = request.getParameter("Warnings");
        int categoryID = Integer.parseInt(request.getParameter("category"));

        Product product = new Product(name, price, foodCost, timeCost, picture, description, nutrientInformation, warnings, categoryID);
        String product_message = "";
        if (productDAO.createProduct(product)) {
            product_message = "Product Added Successfully";
            session.setAttribute("product_message", product_message);
            response.sendRedirect("addProducts.jsp");
        } else {
            product_message = "Failed to add product";
            session.setAttribute("product_message", product_message);
            response.sendRedirect("addProducts.jsp");

        }

    }

}
