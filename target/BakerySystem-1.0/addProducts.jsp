

<%@page import="za.co.bakerysystem.model.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product Page</title>
    </head>
    <body>

        <%
            List<Category> list = (List<Category>) session.getAttribute("listOfCategory");

        %>
        <h1>Add Product</h1>
        <%                  String product_message = (String) session.getAttribute("product_message");
            if (product_message != null) {

        %>
        <div style=""><%=product_message%></div>
        <%
                session.removeAttribute("product_message");
            }
        %>


        <form action="ProductServlet.do" method="post" enctype="multipart/form-data">

            <table border="1">

                <tbody>
                    <tr>
                        <td>Product Name:</td>
                        <td><input type="text" name="Name"></td>
                    </tr>
                    <tr>
                        <td>Product Price</td>
                        <td><input type="text" name="Price"></td>
                    </tr>
                    <tr>
                        <td>Food Cost</td>
                        <td><input type="text" name="FoodCost"></td>
                    </tr>
                    <tr>
                        <td>Time Cost</td>
                        <td><input type="text" name="TimeCost"></td>
                    </tr>
                    <tr>
                        <td>Product Image</td>
                        <td><input type="file" name="Picture"></td>
                    </tr>
                    <tr>
                        <td>Description</td>
                        <td><input type="text" name="Description"></td>
                    </tr>
                    <tr>
                        <td>Nutrient Information</td>
                        <td><input type="text" name="NutrientInformation"></td>
                    </tr>
                    <tr>
                        <td>Warnings</td>
                        <td><input type="text" name="Warnings"></td>
                    </tr>
                    <tr>
                        <td>Category</td>
                        <td>
                            <select name="category">
                                <%    for (Category category : list) {


                                %>
                                <option value="<%=category.getCategoryId()%>"><%=category.getDescription()%></option>
                                <%
                                    }
                                %>

                            </select>

                        </td>
                    </tr>

                    <tr>
                        <td><input type="submit" value="Create Product"></td>
                    </tr>


                </tbody>
            </table>


        </form>

    </body>
</html>
