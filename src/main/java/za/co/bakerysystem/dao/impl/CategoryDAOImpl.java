package za.co.bakerysystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import za.co.bakerysystem.dao.CategoryDAO;
import za.co.bakerysystem.dbmanager.DbManager;
import za.co.bakerysystem.model.Category;

public class CategoryDAOImpl implements CategoryDAO {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private static DbManager db;

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public CategoryDAOImpl() {
        db = DbManager.getInstance();
        connection = db.getConnection();
    }

    // SQL queries
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM Category WHERE categoryId = ?";
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM Category";
    private static final String INSERT_CATEGORY = "INSERT INTO Category (description, picture) VALUES (?,?)";
    private static final String UPDATE_CATEGORY = "UPDATE Category SET description = ? WHERE categoryId = ?";
    private static final String DELETE_CATEGORY = "DELETE FROM Category WHERE categoryId = ?";

    @Override
    public Category getCategoryById(int categoryId) {

        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return extractCategoryFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(ps, rs);
        }
        return null;
    }

    @Override
    public boolean addCategory(Category category) {
        boolean retVal = false;

        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getDescription());
            ps.setBytes(2, category.getPicture());

            retVal = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(ps, null);
        }
        return retVal;

    }

    @Override
    public boolean updateCategory(Category category, int categoryID) {
        boolean retVal = false;

        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(UPDATE_CATEGORY);
            ps.setString(1, category.getDescription());
            ps.setInt(2, category.getCategoryId());
            retVal = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(ps, null);
        }
        return retVal;

    }

    @Override
    public boolean deleteCategory(int categoryId) {
        boolean retVal = false;

        db = DbManager.getInstance();

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(DELETE_CATEGORY);
            ps.setInt(1, categoryId);
            retVal = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(ps, null);
        }
        return retVal;

    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        db = DbManager.getInstance();
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(SELECT_ALL_CATEGORIES);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));

                category.setPicture(rs.getBytes("Picture"));
                category.setDescription(rs.getString("Description"));
                categories.add(category);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }

        return categories;
    }

    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        int categoryId = rs.getInt("categoryId");
        String description = rs.getString("description");
//        bytes[] picture = rs.getBytes("picture");
        return new Category(categoryId, description);
    }

    private void closeResources(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        db = DbManager.getInstance();
        Connection connection = db.getConnection();

        CategoryDAO categoryDAO = new CategoryDAOImpl(connection);

        Category category = new Category("Pies");

        if (categoryDAO.addCategory(category)) {
            System.out.println("Successfully added category");
        } else {
            System.out.println("not added category");

        }
//        List<Category> categories = categoryDAO.getAllCategory();
//        for (Category category1 : categories) {
//            System.out.println(category1); 
//        }
//        Category category1 = categoryDAO.getCategoryById(3);
//
//        System.out.println(category1);
//        if (categoryDAO.deleteCategory(2)) {
//            System.out.println("Success deleting category");
//        } else {
//            System.out.println("Unsuccessful deleting category");
//
//        }
//        if (categoryDAO.updateCategory(new Category(2, "Pies"), 2)) {
//            System.out.println("Success updated category");
//
//        } else {
//            System.out.println("failed updated category");
//
//        }
    }

}
