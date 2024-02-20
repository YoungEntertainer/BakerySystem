<%@ page import="za.co.bakerysystem.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Page</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Add your custom styles here -->
    </head>
    <body>
        <%
            if ((String) session.getAttribute("name") != null) {
        %>
        <jsp:include page="thirdNavbar.jsp"/>
        <%
        } else {
        %>
        <jsp:include page="secondNavbar.jsp"/>
        <%
            }
        %>
    <center>
        <input class="input" name="text" onsubmit="" placeholder="Search..." type="search">
    </center>
    <div class="container">
        <h2 style="font-size: larger;">Our Categories</h2>
        <div class="row">
            <%
                List<Category> listOfCategory = (List<Category>) session.getAttribute("listOfCategory");
                for (Category category : listOfCategory) {
            %>
            <div class="col-md-4 category-card" onclick="redirectToProductServlet('<%= category.getCategoryId()%>')">
                <div class="card">
                    <img src="data:image/png;base64,<%= category.getPictureAsBase64()%>" class="card-img-top" alt=""/>
                    <div class="card-body">
                        <h5 class="card-title"><%= category.getDescription()%></h5>
                        <!-- Add more details about the category if needed -->
                    </div>  
                </div>
            </div>
            <%
                }
            %>
        </div>
        <p><a href="index.jsp">Home</a></p>
    </div>
    <!-- Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- JavaScript function to redirect -->
    <script>
                function redirectToProductServlet(categoryId) {
                    // Check if categoryId is properly received
                    console.log(categoryId);
                    // Redirect to ProductServlet with the categoryId
                    window.location.href = 'ProductServlet.do?categoryId=' + categoryId;
                }
    </script>
</body>
</html>
