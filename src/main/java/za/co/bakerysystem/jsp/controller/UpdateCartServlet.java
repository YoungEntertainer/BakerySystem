package za.co.bakerysystem.jsp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
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

/**
 *
 * @author user
 */
public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

//        int cartID = Integer.parseInt(request.getParameter("cartId"));
        int productID = Integer.parseInt(request.getParameter("productId"));
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));
        //Get product service 
        ProductDAO productDAO = new ProductDAOImpl();
        Product product = productDAO.getProduct(productID);

        //Get shopping cart service
        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
//        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");

        //Initial a total price 
//        double totalAmount = shoppingCart.getTotalAmount() + (product.getPrice() * newQuantity);
//        session.setAttribute("totalAmount", totalAmount);
        
        

//        int cartID = shoppingCart.getCardID();
        // Call the updateShoppingCartQuantity method
        boolean updateSuccess = shoppingCartDAO.updateShoppingCartQuantity(1, productID, newQuantity);

//        shoppingCart.setTotalAmount(totalAmount);
        String message = "";
        // Redirect back to the cart page with a success or error message
        if (updateSuccess) {
            message = "success";
            request.setAttribute("message", message);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else {
            message = "failed";
            request.setAttribute("message", message);
            response.sendRedirect("cart.jsp?message=Update failed");
        }

    }

}
