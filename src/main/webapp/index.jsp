<%@page import="za.co.bakerysystem.service.impl.CategoryServiceImpl"%>
<%@page import="za.co.bakerysystem.service.CategoryService"%>
<%@page import="za.co.bakerysystem.model.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Add your CSS styles here -->
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">


        <title>Home Page</title>
    </head>
    <body class="bg-gray-900">
        <% if ((String) session.getAttribute("name") != null) { %>
        <jsp:include page="thirdNavbar.jsp"/>
        <section style="
                 background-image: url('images/bg.jpg');
                 background-size: cover;
                 background-position: center;
                 background-repeat: no-repeat;
                 height: 100vh;
                 width: 100vw;
                 justify-content: center;
                 ">

        </section>
        <% } else { %>
        <jsp:include page="secondNavbar.jsp"/>
        <section style="
                 background-image: url('images/bg.jpg');
                 background-size: cover;
                 background-position: center;
                 background-repeat: no-repeat;
                 height: 100vh;
                 width: 100vw;
                 justify-content: center;
                 ">

        </section>
        <% }%>
    </body>
</html>
