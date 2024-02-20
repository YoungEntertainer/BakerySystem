package za.co.bakerysystem.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.CustomerDAO;
import za.co.bakerysystem.dao.impl.CustomerDAOImpl;
import za.co.bakerysystem.exception.customer.CustomerDeletionException;
import za.co.bakerysystem.exception.customer.CustomerNotFoundException;
import za.co.bakerysystem.exception.customer.DuplicateEmailException;
import za.co.bakerysystem.exception.customer.DuplicateIdException;
import za.co.bakerysystem.model.Customer;
import za.co.bakerysystem.service.CustomerService;
import za.co.bakerysystem.service.impl.CustomerServiceImpl;

@Path("/customers")
public class CustomerController {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    // private final CustomerDAO customerDAO2 = new CustomerDAOImpl();
    private final CustomerService customerService = new CustomerServiceImpl(customerDAO);

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        String message = "";

        try {

            customerService.exists(customer.getEmail().toLowerCase(), customer.getCustomerIDNo());

            if (customerService.createCustomer(customer)) {
                message = "Signup successful!";
                return Response.status(Response.Status.CREATED).entity(message).build();
            }
        } catch (DuplicateEmailException | DuplicateIdException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Customer customer) {
        Customer loggedInCustomer = customerService.login(customer.getEmail(), customer.getPassword());
        if (loggedInCustomer != null) {
            return Response.ok(customerService.getCustomerByEmail(customer.getEmail())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed. Invalid credentials.").build();
        }
    }

    @GET
    @Path("/get/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersById(@PathParam("customerId") int customerId) {
        try {

            return Response.ok(customerService.getCustomer(customerId)).build();

        } catch (CustomerNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }

    }

    @PUT
    @Path("/update/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(Customer updatedCustomer, @PathParam("customerId") int customerId) {
        if (customerService.updateCustomer(updatedCustomer, customerId)) {
            return Response.ok("Customer updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
    }

    @DELETE
    @Path("/delete/{customerId}")
    public Response deleteCustomer(@PathParam("customerId") int customerId) {
        try {
            if (customerService.deleteCustomer(customerId)) {
                return Response.ok("Customer deleted successfully").build();
            }
        } catch (CustomerNotFoundException | CustomerDeletionException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        List<Customer> allCustomers = customerService.getCustomers();

        if (allCustomers != null && !allCustomers.isEmpty()) {
            return Response.ok(allCustomers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No customers found").build();
        }
    }

    @GET
    @Path("/keyword/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersByKeyword(@PathParam("keyword") String keyword) {
        List<Customer> allCustomers = customerService.getCustomersByKeyWord(keyword);

        if (allCustomers != null && !allCustomers.isEmpty()) {
            return Response.ok(allCustomers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No customers found").build();
        }
    }

    @GET
    @Path("/total_customer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerQuantity() {
        int count = customerService.getCustomersQuantity();

        if (count > 0) {
            return Response.ok(count).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No customers found").build();
        }
    }

    @GET
    @Path("/get_by_email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerByEmail(@PathParam("email") String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer != null) {
            return Response.ok(customer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found for the provided email").build();
        }
    }

}
