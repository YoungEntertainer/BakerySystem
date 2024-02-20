package za.co.bakerysystem.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dao.ShoppingCartDAO;
import za.co.bakerysystem.dao.impl.ProductDAOImpl;
import za.co.bakerysystem.dao.impl.ShoppingCartDAOImpl;
import za.co.bakerysystem.model.Product;
import za.co.bakerysystem.model.ShoppingCart;
import za.co.bakerysystem.service.ShoppingCartService;
import za.co.bakerysystem.service.impl.ShoppingCartServiceImpl;

@Path("/shopping_carts")
public class ShoppingCartController {

    private final ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDAO, productDAO);

    @GET
    @Path("/get_shoppingcart/{cartID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppingCartById(@PathParam("cartID") int cartID) {
        if (cartID <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart ID must be greater than 0").build();
        }

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(cartID);

        if (shoppingCart != null) {
            return Response.ok(shoppingCart).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Shopping cart not found").build();
        }
    }

    @POST
    @Path("/add_product/{cartID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProductToCart(
            Product product,
            @PathParam("cartID") int cartID,
            @QueryParam("quantity") int quantity) {
        if (cartID <= 0 || quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart ID and quantity must be greater than 0").build();
        }

        boolean success = shoppingCartService.addProductToCart(cartID, product, quantity);

        if (success) {
            return Response.status(Response.Status.CREATED).entity("Product added to cart successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add product to cart").build();
        }
    }

    @DELETE
    @Path("/remove_product/{cartID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeProductFromCart(Product product,
            @PathParam("cartID") int cartID
    ) {
        if (cartID <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart ID must be greater than 0").build();
        }

        boolean success = shoppingCartService.removeProductFromCart(cartID, product);

        if (success) {
            return Response.ok("Product removed from cart successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found in the cart").build();
        }
    }

    @GET
    @Path("/total_amount/{cartID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateTotalAmount(@PathParam("cartID") int cartID) {
        if (cartID <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart ID must be greater than 0").build();
        }

        double totalAmount = shoppingCartService.calculateTotalAmount(cartID);

        return Response.ok(totalAmount).build();
    }
}
