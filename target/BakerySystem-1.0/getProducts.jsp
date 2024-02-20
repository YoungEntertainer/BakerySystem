<%@ page import="java.util.List" %>
<%@ page import="za.co.bakerysystem.model.Product" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products Page</title>

        <!-- Bootstrap CSS -->
        <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> -->
        <!-- Add your custom styles here -->
        <!-- <link rel="stylesheet" href="assets/css/tailwind.css" /> -->
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>


        <script>
            function confirmAndAddToCart(productId) {
                var confirmed = confirm("Add product to cart?");

                if (confirmed) {
                    // Make AJAX request to addToCart.do servlet
                    $.ajax({
                        url: 'CartServlet.do', // Replace 'addToCart.do' with the actual servlet URL
                        method: 'POST',
                        data: {productId: productId},
                        success: function (response) {
                            // Handle success response
                            console.log('Product added to cart successfully');
                            Swal.fire({
                                icon: 'success',
                                title: 'Added to cart successfully',
//                text: 'Welcome back, ' + username + '!',
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: 'Continue'
                            });
                        },
                        error: function (xhr, status, error) {
                            // Handle error
                            console.error('Error adding product to cart:', error);
                            alert('Error adding product to cart');
                        }
                    });
                }
            }
        </script>
    </head>
    <body class="bg-gray-1">
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

        <%
            List<Product> productList = (List<Product>) session.getAttribute("productList");
            String message = (String) request.getAttribute("msg");
        %>

        <%
            if (message != null) {
                if (message.equalsIgnoreCase("success")) {
        %>
        <p class="card-text" style="color:green;"><%=message%></p>
        <%
        } else {
        %>
        <script>

            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Failed to add to cart',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Try Again'
            });
        </script>
        <%
            }
        } else {
        %>
        <p></p>
        <%
            }
        %>

    <center><h1 style="font-family: serif;align-content: center;font-size: 60px">Products</h1></center>

    <section class="bg-gray-2 pb-10 pt-20 dark:bg-white lg:pb-20 lg:pt-[120px]">
        <form action="CartServlet.do" method="POST">
            <div class="container mx-auto">
                <div class="-mx-3 flex flex-wrap">
                    <%
                        if (productList != null && !productList.isEmpty()) {
                            for (Product product : productList) {
                    %>
                    <div class="w-full px-2 md:w-1/2 xl:w-1/3">
                        <div class="mb-10 overflow-hidden rounded-lg bg-gray-900 shadow-1 duration-300 hover:shadow-3 dark:bg-dark-2 dark:shadow-card dark:hover:shadow-3">
                            <img src="data:image/png;base64,<%= product.getPictureAsBase64()%>" alt="product image" class="w-full" />
                            <div class="p-8 text-center sm:p-9 md:p-7 xl:p-9">
                                <h3>
                                    <a href="javascript:void(0)" class="mb-4 block text-xl font-semibold text-white hover:text-primary dark:text-white sm:text-[22px] md:text-xl lg:text-[22px] xl:text-xl 2xl:text-[22px]">
                                        <%= product.getName()%>
                                    </a>
                                </h3>
                                <h3 class="mb-7 text-base leading-relaxed text-white dark:text-dark-6">
                                    <%= product.getDescription()%>
                                </h3>
                                <h4 class="mb-7 text-base leading-relaxed text-white dark:text-dark-6">
                                    R <%= product.getPrice()%>
                                </h4>
                                <input type="hidden" name="productId" value="<%=product.getID()%>">
                                <input type="button" onclick="confirmAndAddToCart('<%=product.getID()%>')" value="Add to cart" class="inline-block rounded-full border border-gray-3 px-7 py-2 text-base font-medium text-body-color transition hover:border-primary hover:bg-gray-900 hover:text-white dark:border-dark-3 dark:text-dark-6">
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <script>
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'No Product found',
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'OK'
                        });

                    </script>
                    <%
                        }
                    %>
                </div>
            </div>
        </form>
        <!-- ====== Cards Section End -->



    </section>

</body>
</html>
