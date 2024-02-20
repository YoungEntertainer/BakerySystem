<%-- 
    Document   : cart2
    Created on : 05 Feb 2024, 9:53:34 PM
    Author     : user
--%>
<%@ page import="za.co.bakerysystem.model.ShoppingCart" %>
<%@ page import="za.co.bakerysystem.model.Product" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Add your custom styles here -->
        <link href="css/cart.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

        <style>

            .cart-item {
                border-bottom: 1px solid #dee2e6;
                margin-bottom: 10px;
                padding-bottom: 10px;
            }

            .total-amount {
                font-weight: bold;
            }

            .update-form {
                margin-top: 10px;
            }

            .delete-btn {
                color: red;
                cursor: pointer;
            }

            button {
                width: 150px;
                height: 50px;
                cursor: pointer;
                display: flex;
                align-items: center;
                background: red;
                border: none;
                border-radius: 5px;
                box-shadow: 1px 1px 3px rgba(0,0,0,0.15);
                background: #e62222;
            }

            button, button span {
                transition: 200ms;
            }

            button .text {
                transform: translateX(35px);
                color: white;
                font-weight: bold;
            }

            button .icon {
                position: absolute;
                border-left: 1px solid #c41b1b;
                transform: translateX(110px);
                height: 40px;
                width: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            button svg {
                width: 15px;
                fill: #eee;
            }

            button:hover {
                background: #ff3636;
            }

            button:hover .text {
                color: transparent;
            }

            button:hover .icon {
                width: 150px;
                border-left: none;
                transform: translateX(0);
            }

            button:focus {
                outline: none;
            }

            button:active .icon svg {
                transform: scale(0.8);
            }
        </style>
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



        <% ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");

            List<Product> cartItems = shoppingCart.getProducts();
            String message = (String) request.getAttribute("message");

            double totalAmount = shoppingCart.getTotalAmount();

        %>

        <% if (message != null) { %>
        <% if (message.equalsIgnoreCase("success")) {%>
        <p class="card-text" style="color:green;"><%=message%></p>
        <% } else {%>
        <p class="card-text" style="color:red;"><%=message%></p>
        <% } %>
        <% } else { %>
        <p></p>
        <% } %>




        <!--testing-->


        <!--testing 2-->
        <div class="container mx-auto mt-10">
            <div class="flex shadow-md my-10">
                <div class="w-3/4 bg-white px-10 py-10">
                    <div class="flex justify-between border-b pb-8">
                        <h1 class="font-semibold text-2xl">Shopping Cart</h1>
                    </div>
                    <div class="flex mt-10 mb-5">
                        <h3 class="font-semibold text-gray-600 text-xs uppercase w-2/5">Product Details</h3>
                        <h3 class="font-semibold text-center text-gray-600 text-xs uppercase w-1/5 text-center">Quantity</h3>
                        <h3 class="font-semibold text-center text-gray-600 text-xs uppercase w-1/5 text-center">Price</h3>
                        <h3 class="font-semibold text-center text-gray-600 text-xs uppercase w-1/5 text-center">Total</h3>
                    </div>
                    <% if (cartItems != null && !cartItems.isEmpty()) { %>
                    <% for (Product product : cartItems) {%>


                    <div class="flex items-center hover:bg-gray-100 -mx-8 px-6 py-5">
                        <div class="flex w-2/5"> <!-- product -->
                            <div class="w-20">
                                <img src="data:image/png;base64,<%= product.getPictureAsBase64()%>" height="60" width="60" alt="alt"/>

                            </div>
                            <div class="flex flex-col justify-between ml-4 flex-grow">
                                <span class="font-bold text-sm"><%= product.getName()%></span>
                                <span class="text-red-500 text-xs"><%= product.getDescription()%></span>
                                <a href="#" onclick="removeProduct('<%= product.getID()%>'); return false;" class="font-semibold hover:text-red-500 text-gray-500 text-xs">Remove</a>

                            </div>
                        </div>
                        <div class="flex justify-center w-1/5">
                            <svg class="fill-current text-gray-600 w-3" viewBox="0 0 448 512"><path d="M416 208H32c-17.67 0-32 14.33-32 32v32c0 17.67 14.33 32 32 32h384c17.67 0 32-14.33 32-32v-32c0-17.67-14.33-32-32-32z"/>
                            </svg>

                            <form onsubmit="updateQuantity('<%= product.getID()%>', this.quantity.value); return false;" class="update-form">
                                <input type="hidden" name="productId" value="<%= product.getID()%>">
                                <input type="number" name="quantity" value="1" min="1" placeholder="Quantity">
                                <button type="submit" class="btn btn-sm btn-primary">Update Quantity</button>
                            </form>




                            <svg class="fill-current text-gray-600 w-3" viewBox="0 0 448 512">
                            <path d="M416 208H272V64c0-17.67-14.33-32-32-32h-32c-17.67 0-32 14.33-32 32v144H32c-17.67 0-32 14.33-32 32v32c0 17.67 14.33 32 32 32h144v144c0 17.67 14.33 32 32 32h32c17.67 0 32-14.33 32-32V304h144c17.67 0 32-14.33 32-32v-32c0-17.67-14.33-32-32-32z"/>
                            </svg>
                        </div>
                        <span id="unitPrice_<%= product.getID()%>" class="text-center w-1/5 font-semibold text-sm">R<%= product.getPrice()%></span>
                        <span class="text-center w-1/5 font-semibold text-sm">R<%= product.getPrice()%></span>
                    </div>
                    <% }%>



                    <a href="CategoryServlet.do" class="flex font-semibold text-indigo-600 text-sm mt-10">

                        <svg class="fill-current mr-2 text-indigo-600 w-4" viewBox="0 0 448 512"><path d="M134.059 296H436c6.627 0 12-5.373 12-12v-56c0-6.627-5.373-12-12-12H134.059v-46.059c0-21.382-25.851-32.09-40.971-16.971L7.029 239.029c-9.373 9.373-9.373 24.569 0 33.941l86.059 86.059c15.119 15.119 40.971 4.411 40.971-16.971V296z"/></svg>
                        Continue Shopping
                    </a>
                </div>

                <div id="summary" class="w-1/4 px-8 py-10">
                    <h1 class="font-semibold text-2xl border-b pb-8">Order Summary</h1>
                    <div class="flex justify-between mt-10 mb-5">
                        <!--<span class="font-semibold text-sm uppercase">Items 3</span>-->
                        <span id="totalAmount" class="font-semibold text-sm">R<%= totalAmount%></span>
                    </div>
                    <div class="border-t mt-8">
                        <div class="flex font-semibold justify-between py-6 text-sm uppercase">
                            <span>Total cost</span>
                            <span id="totalAmount" >R<%= totalAmount%></span>
                        </div>
                        <button class="bg-indigo-500 font-semibold hover:bg-indigo-600 py-3 text-sm text-white uppercase w-full">Checkout</button>
                    </div>
                </div>

            </div>
            <% } else { %>
            <p>Shopping cart is empty.</p>
            <% }%>
        </div>

        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <!-- JavaScript function to handle product removal -->
        <script>
                                // Function to remove product using AJAX
                                function removeProduct(productId) {
                                    // Ask for confirmation
                                    var confirmation = confirm("Are you sure you want to remove this product?");

                                    // If user confirms, proceed with removal
                                    if (confirmation) {
                                        $.ajax({
                                            url: 'RemoveProductServlet.do',
                                            method: 'POST',
                                            data: {productId: productId},
                                            success: function (response) {
                                                // Refresh content after successful removal
                                                location.reload(); // Reload the page to reflect the changes immediately
                                            },
                                            error: function (xhr, status, error) {
                                                console.error('Error removing product:', error);
                                            }
                                        });
                                    }
                                }




                                // Function to update quantity and total amount using AJAX
                                function updateQuantity(productId, quantity) {
                                    var unitPriceString = $('#unitPrice_' + productId).text().replace('R', '').trim(); // Get unit price string
                                    var unitPrice = parseFloat(unitPriceString); // Parse unit price to float
                                    var newQuantity = parseInt(quantity); // Parse quantity to integer

                                    // Check if unit price and quantity are valid numbers
                                    if (!isNaN(unitPrice) && !isNaN(newQuantity)) {
                                        var newTotalAmount = unitPrice * newQuantity; // Calculate new total amount

                                        // Update total amount element
                                        $('#totalAmount').text('R' + newTotalAmount.toFixed(2)); // Update the total amount element
                                    } else {
                                        console.error('Invalid unit price or quantity');
                                    }

                                    // Perform AJAX request to update quantity
                                    $.ajax({
                                        url: 'UpdateCartServlet.do', // URL of your servlet to update quantity
                                        method: 'POST',
                                        data: {productId: productId, quantity: quantity},
                                        success: function (response) {
                                            // Refresh content after successful update
                                            $('#content').html(response);
                                            location.reload(); // Reload the page to reflect the changes immediately
                                            
                                        },
                                        error: function (xhr, status, error) {
                                            console.error('Error updating quantity:', error);
                                        }
                                    });
                                }



        </script>

    </body>
</html>
