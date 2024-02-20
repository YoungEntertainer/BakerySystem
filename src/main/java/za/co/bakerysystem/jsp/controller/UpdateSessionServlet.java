package za.co.bakerysystem.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateSessionServlet.do")
public class UpdateSessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        // Retrieve the cartCounter value from the request
        int cartCounter = Integer.parseInt(request.getParameter("cartCounter"));

        // Update the server-side session with the new cartCounter value
        session.setAttribute("cartCounter", cartCounter);

        // Send a JSON response indicating success
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"status\": \"success\"}");
        out.flush();
    }
}
