<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Nav bar</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

        <link href="css/custom.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <nav>
            <a href="customer-login.jsp">Login</a>
            <a href="customer-register.jsp">Register</a>

            <a href="CategoryServlet.do">Menu</a>
            <a href="index.jsp">Home</a>

            <div class="cart-icon">
                <a href="#"><i class="fa fa-shopping-cart"></i>
                    <span id="cartCounter" class="badge badge-pill badge-success">0</span>
                </a>

            </div>
        </nav>



    </body>
</html>
