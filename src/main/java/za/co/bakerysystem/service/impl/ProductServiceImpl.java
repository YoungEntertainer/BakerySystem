package za.co.bakerysystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import za.co.bakerysystem.dao.ProductDAO;
import za.co.bakerysystem.model.Product;
import za.co.bakerysystem.service.ProductService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.bakerysystem.dao.impl.ProductDAOImpl;
import za.co.bakerysystem.exception.product.DuplicateProductException;
import za.co.bakerysystem.exception.product.ProductNotFoundException;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductServiceImpl() {
        this.productDAO = new ProductDAOImpl();
    }

    @Override
    public boolean createProduct(Product product) {
        return productDAO.createProduct(product);
    }

    @Override
    public List<Product> getOrderProduct(int orderID) {
        return productDAO.getOrderProduct(orderID);
    }

    @Override
    public List<Product> getProductsForShoppingCart(int cartID) {
        return productDAO.getProductsForShoppingCart(cartID);
    }

    @Override
    public List<Product> getFavoriteProducts(int customerID) {
        return productDAO.getFavoriteProducts(customerID);
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    @Override
    public List<Product> getProducts() {
        return productDAO.getProducts();
    }

    @Override
    public List<Product> getProductsByKeyWord(String keyWord) {
        return productDAO.getProductsByKeyWord(keyWord);
    }

    @Override
    public List<Product> getAllProductByCategory(int categoryID) {
        return productDAO.getAllProductByCategory(categoryID);
    }

    @Override
    public List<Product> getRelatedProducts(int ingredientID) {
        return productDAO.getRelatedProducts(ingredientID);
    }

    @Override
    public Product getProduct(int productID) throws ProductNotFoundException {

        if (productDAO.getProduct(productID) == null) {
            throw new ProductNotFoundException("Product " + productID + " not found");

        }
        return productDAO.getProduct(productID);

    }

    @Override
    public int getProductQuantity() {
        return productDAO.getProductQuantity();
    }

    @Override
    public boolean deleteProduct(int productID) {
        return productDAO.deleteProduct(productID);
    }

    @Override
    public boolean exists(String name) throws DuplicateProductException {
        if (productDAO.getProducts().stream().anyMatch(product -> product.getName().equalsIgnoreCase(name.toLowerCase()))) {
            throw new DuplicateProductException("Product already exist");
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            ProductDAO productDAO = new ProductDAOImpl();
            ProductServiceImpl productService = new ProductServiceImpl(productDAO);

            File imageFile = new File("C:\\Users\\Train\\Downloads\\draft3.webp");

            byte[] pictureData = Files.readAllBytes(imageFile.toPath());
            Product product = new Product("Yellow Cake", 54.99, 8.50, 3, pictureData, "High in chocolate", "fibre and calcium", "none", 1);
            // test for add product
            if (productService.createProduct(product)) {
                System.out.println("Success");

            } else {
                System.out.println("Failed");

            }
            // Test updateProduct
//        int productIdToUpdate = 8; // Replace with a valid product ID
//        Product productToUpdate = productService.getProduct(productIdToUpdate);
//        if (productToUpdate != null) {
//            productToUpdate.setName("Updated Bread");
//            boolean productUpdated = productService.updateProduct(productToUpdate);
//            System.out.println("Updating Product: " + productUpdated);
//        } else {
//            System.out.println("Product not found for updating.");
//        }
//        // Test getProducts
//        List<Product> allProducts = productService.getProducts();
//        System.out.println("All Products: " + allProducts);
// Test getProduct
//        int productIdToGet = 6; // Replace with a valid product ID
//        Product retrievedProduct = productService.getProduct(productIdToGet);
//        System.out.println("Retrieved Product: " + retrievedProduct);
//        // Test deleteProduct
//        boolean productDeleted = productService.deleteProduct(productIdToGet);
//        System.out.println("Deleting Product: " + productDeleted);
//        System.out.println(productService.getProductQuantity());
//Test product for shopping cart
//System.out.println(productService.getProductsForShoppingCart(1));
           // System.out.println(productDAO.getProduct(100));
        } catch (IOException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
