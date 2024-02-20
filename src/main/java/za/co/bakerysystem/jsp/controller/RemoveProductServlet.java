package za.co.bakerysystem.jsp.controller;

import java.io.IOException;
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
import za.co.bakerysystem.model.ShoppingCart;

@WebServlet("/RemoveProductServlet")
public class RemoveProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract productId from the request parameters
        int productId = Integer.parseInt(request.getParameter("productId"));

        ProductDAO productDAO = new ProductDAOImpl();
        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();

        // Retrieve the shopping cart from the session
        HttpSession session = request.getSession();
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCartById(1);

        // Check if the shopping cart is not null
        if (shoppingCart != null) {
            // Use ShoppingCartDAO to remove the specified product from the cart

            Product productToRemove = productDAO.getProduct(productId);

            boolean removalSuccess = shoppingCartDAO.removeProductFromCart(shoppingCart, productToRemove);

            // Optionally, you can set a message in the request attribute to display in the JSP
            if (removalSuccess) {
                request.setAttribute("message", "success");
            } else {
                request.setAttribute("message", "failed");
            }
        } else {
            // Handle the case where the shopping cart is null (possibly not initialized)
            request.setAttribute("message", "Shopping cart not found");
        }

        // Forward the request to the JSP page (UpdateCart.jsp or your cart page)
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
