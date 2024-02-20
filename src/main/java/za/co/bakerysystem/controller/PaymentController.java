package za.co.bakerysystem.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import za.co.bakerysystem.dao.PaymentDAO;
import za.co.bakerysystem.dao.impl.PaymentDAOImpl;
import za.co.bakerysystem.model.Payment;
import za.co.bakerysystem.model.PaymentType;
import za.co.bakerysystem.service.PaymentService;
import za.co.bakerysystem.service.impl.PaymentServiceImpl;

@Path("/payments")
public class PaymentController {

    private final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final PaymentService paymentService = new PaymentServiceImpl(paymentDAO);

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayment(Payment payment) {
        // Perform additional validation if needed
        if (payment.getAmount() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Payment amount must be greater than 0").build();
        }

        if (paymentService.createPayment(payment)) {
            return Response.status(Response.Status.CREATED).entity("Payment created successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create payment").build();
        }
    }

    @GET
    @Path("/order_payment/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderPayment(@PathParam("orderId") int orderId) {
        List<Payment> orderPayments = paymentService.getOrderPayment(orderId);

        if (orderPayments != null && !orderPayments.isEmpty()) {
            return Response.ok(orderPayments).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No payment information found for the order").build();
        }
    }

    @DELETE
    @Path("/delete/{orderID}")
    public Response deletePayment(@PathParam("orderID") int orderID) {
        if (orderID <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Order ID must be greater than 0").build();
        }

        if (paymentService.deletePayment(orderID)) {
            return Response.ok("Payment deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Payment not found").build();
        }
    }

    @GET
    @Path("/order_payments/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderPayments(@PathParam("orderID") int orderID) {
        if (orderID <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Order ID must be greater than 0").build();
        }

        List<Payment> orderPayments = paymentService.getOrderPayments(orderID);

        if (orderPayments != null && !orderPayments.isEmpty()) {
            return Response.ok(orderPayments).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No payments found for the specified order").build();
        }
    }

    
    @GET
    @Path("/payment_types")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentTypes() {
        List<PaymentType> paymentTypes = paymentService.getPaymentTypes();

        if (paymentTypes != null && !paymentTypes.isEmpty()) {
            return Response.ok(paymentTypes).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No payment types found").build();
        }
    }
}
