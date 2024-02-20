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
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int productId = Integer.parseInt(request.getParameter("productId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
        ProductDAO productDAO = new ProductDAOImpl();
        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();

        Product product = productDAO.getProduct(productId);

        String message = "";

        if (product != null) {
            int cartID = 1; // You may obtain cart ID from the user's session or other sources

            // Add the product to the shopping cart in the database
            boolean added = shoppingCartDAO.addProductToCart(cartID, product, 0);

            if (!added) {

                message = "failed";
                request.setAttribute("msg", message);
                System.out.println("Failed");

                request.getRequestDispatcher("getProducts.jsp").forward(request, response);
            } else {
                // Redirect back to the product list or wherever appropriate

                int counter = (int) session.getAttribute("counter");

                counter++;
                message = "success";
                request.setAttribute("msg", message);

                request.getRequestDispatcher("getProducts.jsp").forward(request, response);
            }

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int cartID = 1;

        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCartById(cartID);

        session.setAttribute("shoppingCart", shoppingCart);

        request.getRequestDispatcher("cart.jsp").forward(request, response);

    }
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//         int productId = Integer.parseInt(request.getParameter("productId"));
//
//        int cartID = 1; // You may obtain cart ID from the user's session or other sources
//        ProductDAO productDAO = new ProductDAOImpl();
//        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
////
////        // Remove the product from the shopping cart in the database
//        boolean removed = shoppingCartDAO.removeProductFromCart(cartID, productDAO.getProduct(productId));
//
////
//        if (removed) {
////            // Redirect back to the cart view
//            response.sendRedirect(request.getContextPath() + "cart.jsp");
//        } else {
////            // Handle the case where removing from the cart fails
//            response.sendRedirect(request.getContextPath() + "error.jsp");
////        }
//        }
//    }
//    
    
    

//    private void removeItemFromCart(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int productId = Integer.parseInt(request.getParameter("productId"));
//
//        int cartID = 1; // You may obtain cart ID from the user's session or other sources
//        ProductDAO productDAO = new ProductDAOImpl();
//        ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
////
////        // Remove the product from the shopping cart in the database
//        boolean removed = shoppingCartDAO.removeProductFromCart(cartID, productDAO.getProduct(productId));
//
////
//        if (removed) {
////            // Redirect back to the cart view
////            response.sendRedirect(request.getContextPath() + "/cart?action=view");
//        } else {
////            // Handle the case where removing from the cart fails
////            response.sendRedirect(request.getContextPath() + "/error.jsp");
////        }
//        }
//    }

}
