<%-- 
    Document   : addCustomer
    Created on : 04 Feb 2024, 6:36:32 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10">

    </head>
    <body>
        <jsp:include page="secondNavbar.jsp" />

        <%
            String success = (String) session.getAttribute("success-message");
            if (success != null) {
                session.removeAttribute("success-message");
        %>
        <!-- Include SweetAlert JS -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <script>
            // Show success message using SweetAlert
            Swal.fire({
                icon: 'success',
                title: 'Registered successfully',
//                text: 'Welcome back, ' + username + '!',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Continue'
            });
        </script>
        <%
            }
            String fail = (String) session.getAttribute("fail-message");
            if (fail != null) {
                session.removeAttribute("fail-message");
        %>
        <script>
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Ragistration Failed',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Try Again'
            });

        </script>
        <%
            }
        %>


        <!--Test Register Form-->

        <form action="AddCustomer.do" method="post" style="color: #000">
            <div class="relative py-3 sm:max-w-xl sm:mx-auto">

                <div
                    class="relative px-4 py-10 bg-white mx-8 md:mx-0 shadow rounded-3xl sm:p-10"
                    >

                    <div class="max-w-md mx-auto">
                        <div class="mt-5 grid grid-cols-1 sm:grid-cols-2 gap-5">
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="customer Name"
                                    >Full Name</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="text"
                                    id="customerName"
                                    name="customerName"
                                    required=""
                                    placeholder="Enter your name"
                                    />
                            </div>

                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="id number"
                                    >ID/Passport Number</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="text"
                                    id="username"
                                    name="customerIDNo"
                                    required=""

                                    placeholder="Enter your ID/Passport Number"

                                    />
                            </div>
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="phone number"

                                    >Phone Number</label

                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="text"
                                    id="username"
                                    name="phoneNumber"
                                    required=""

                                    placeholder="Enter your phone number"

                                    />
                            </div>
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="address"
                                    >Address</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="text"
                                    id="username"
                                    name="addressOne"
                                    required=""
                                    placeholder="Enter your address"

                                    />
                            </div>
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="city"
                                    >City</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="text"
                                    id="username"
                                    required=""
                                    placeholder="Enter your city"

                                    name="city"
                                    />
                            </div>
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="zip code"
                                    >Zip Code</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="text"
                                    id="username"
                                    required=""
                                    placeholder="Enter your zip cope"

                                    name="zip"
                                    />
                            </div>
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="email"
                                    >Email</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="email"
                                    id="email"
                                    name="email"
                                    required=""
                                    placeholder="Enter your email"

                                    />
                            </div>
                            <div>
                                <label
                                    class="font-semibold text-sm text-gray-600 pb-1 block"
                                    for="password"
                                    >Password</label
                                >
                                <input
                                    class="border rounded-lg px-3 py-2 mt-1 mb-5 text-sm w-full focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                                    type="password"
                                    id="password"
                                    required=""
                                    name="password"
                                    placeholder="Enter your password"

                                    />
                            </div>
                        </div>


                        <div class="mt-5">
                            <button
                                class="py-2 px-4 bg-gray-900 hover:bg-gray-500 focus:ring-blue-500 focus:ring-offset-blue-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg"
                                type="submit"
                                onsubmit=""
                                >
                                Sign up
                            </button>
                        </div>
                        <div class="flex items-center justify-between mt-4">
                            <span class="w-1/5 border-b dark:border-gray-600 md:w-1/4"></span>
                            <a
                                class="text-xs text-gray-500 uppercase dark:text-gray-400 hover:underline"
                                href="customer-login.jsp"
                                >have an account? Log in</a
                            >
                            <span class="w-1/5 border-b dark:border-gray-600 md:w-1/4"></span>
                        </div>
                    </div>
                </div>
            </div>
        </form>   

        <!--end test-->



    </body>
</html>
