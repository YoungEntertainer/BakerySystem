package za.co.bakerysystem.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.dao.impl.ProductDAOImpl;
import za.co.bakerysystem.exception.product.DuplicateProductException;
import za.co.bakerysystem.exception.product.ProductNotFoundException;
import za.co.bakerysystem.model.Product;
import za.co.bakerysystem.service.ProductService;
import za.co.bakerysystem.service.impl.ProductServiceImpl;

@Path("/products")

public class ProductController {

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ProductService productService = new ProductServiceImpl(productDAO);

    @POST
    @Path("/add_product")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {

        try {
            productService.exists(product.getName());

            if (productService.createProduct(product)) {
                return Response.status(Response.Status.CREATED).entity("Product added successfully").build();
            }
        } catch (DuplicateProductException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Product was not added successfully, check your server").build();

    }

    @GET
    @Path("/order_product/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderProduct(@PathParam("orderId") int orderId) {
        List<Product> orderProducts = productService.getOrderProduct(orderId);

        if (orderProducts != null && !orderProducts.isEmpty()) {
            return Response.ok(orderProducts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No product information found for the order").build();
        }
    }

    @GET
    @Path("/get_product/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("productId") int productId) {
        try {
            if (productService.getProduct(productId) != null) {
                return Response.ok(productService.getProduct(productId)).build();
            }

        } catch (ProductNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Internal Server Error").build();

    }

    @DELETE
    @Path("/delete_product/{productId}")
    public Response deleteProducts(@PathParam("productId") int productId) {
        if (productService.deleteProduct(productId)) {
            return Response.ok("Product deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
    }

    @GET
    @Path("/all_products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<Product> allProducts = productService.getProducts();

        if (allProducts != null && !allProducts.isEmpty()) {
            return Response.ok(allProducts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No product found").build();
        }
    }

    @PUT
    @Path("/update_product/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product updatedProduct) {
        if (productService.updateProduct(updatedProduct)) {
            return Response.ok("Product updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
    }

    @GET
    @Path("/keyword/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByKeyword(@PathParam("keyword") String keyword) {
        List<Product> allProducts = productService.getProductsByKeyWord(keyword);

        if (allProducts != null && !allProducts.isEmpty()) {
            return Response.ok(allProducts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No products found").build();
        }
    }

    @GET
    @Path("/get_product_cart/{productID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductForShoppingCart(@PathParam("productID") int productID) {
        List<Product> allProducts = productService.getProductsForShoppingCart(productID);

        if (allProducts != null && !allProducts.isEmpty()) {
            return Response.ok(allProducts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No products found").build();
        }
    }

    @GET
    @Path("/get_product_catergory/{categoryID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductByCategory(@PathParam("categoryID") int categoryID) {
        List<Product> allProducts = productService.getAllProductByCategory(categoryID);

        if (allProducts != null && !allProducts.isEmpty()) {
            return Response.ok(allProducts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No products found").build();
        }
    }

    @GET
    @Path("/total_products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerQuantity() {
        int count = productService.getProductQuantity();

        if (count > 0) {
            return Response.ok(count).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No products found").build();
        }
    }

    @GET
    @Path("/favorite_products/{customerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavoriteProducts(@PathParam("customerID") int customerID) {
        List<Product> favoriteProducts = productService.getFavoriteProducts(customerID);

        if (favoriteProducts != null && !favoriteProducts.isEmpty()) {
            return Response.ok(favoriteProducts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/ingredient_products/{ingredientID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRelatedProducts(@PathParam("ingredientID") int ingredientID) {

        List<Product> allProduct = productService.getRelatedProducts(ingredientID);
//        List<Ingredient> allIngredients = ingredientService.getIngredients();

        if (allProduct != null && !allProduct.isEmpty()) {
            return Response.ok(allProduct).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No ingredients found").build();
        }
    }

    @GET
    @Path("/products/{cartID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsForShoppingCart(@PathParam("cartID") int cartID) {
        if (cartID <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart ID must be greater than 0").build();
        }

        List<Product> products = productService.getProductsForShoppingCart(cartID);

        if (products != null && !products.isEmpty()) {
            return Response.ok(products).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No products found for the specified cart").build();
        }
    }

}
