package za.co.bakerysystem.jsp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dao.ShoppingCartDAO;
import za.co.bakerysystem.dao.impl.ProductDAOImpl;
import za.co.bakerysystem.dao.impl.ShoppingCartDAOImpl;
import za.co.bakerysystem.model.Product;

/**
 *
 * @author user
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet.do"})
public class AddToCartServlet extends HttpServlet {

    private final ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();

    ;
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        
        List<Product> productList = (List<Product>) session.getAttribute("productList");
        

        Product product = productDAO.getProduct(0);


        String message = "";

        if (shoppingCartDAO.addProductToCart(1, product, 4)) // Retrieve product ID from the request
        {
            message = "successfull added to cart";
            session.setAttribute("message", message);
        }

        message = "Failed adding to cart";

        session.setAttribute("message", message);
        request.getRequestDispatcher("getProduct.jsp").forward(request, response);

    }
}
