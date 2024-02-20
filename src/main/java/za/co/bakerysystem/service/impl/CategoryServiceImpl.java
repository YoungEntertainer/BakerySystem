package za.co.bakerysystem.service.impl;

import java.util.List;
import za.co.bakerysystem.dao.CategoryDAO;
import za.co.bakerysystem.dao.impl.CategoryDAOImpl;
import za.co.bakerysystem.exception.category.DuplicateCategoryExcpetion;
import za.co.bakerysystem.model.Category;
import za.co.bakerysystem.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public CategoryServiceImpl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Category getCategoryById(int categoryID) {
        return categoryDAO.getCategoryById(categoryID);
    }

    @Override
    public boolean addCategory(Category category) {

        return categoryDAO.addCategory(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryDAO.getAllCategory();
    }

    @Override
    public boolean updateCategory(Category category, int categoryId) {
        return categoryDAO.updateCategory(category, categoryId);
    }

    @Override
    public boolean deleteCategory(int categoryID) {
        return categoryDAO.deleteCategory(categoryID);
    }

    @Override
    public boolean exists(String description) throws DuplicateCategoryExcpetion {

        if (categoryDAO.getAllCategory().stream().anyMatch(category -> category.getDescription().equalsIgnoreCase(description))) {
            throw new DuplicateCategoryExcpetion("Category provided already exist");
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        CategoryService categoryService = new CategoryServiceImpl(categoryDAO);

        // Testing getCategoryById
//        int categoryIdToRetrieve = 1;
//        Category retrievedCategory = categoryService.getCategoryById(categoryIdToRetrieve);
//        System.out.println("Category retrieved by ID " + categoryIdToRetrieve + ": " + retrievedCategory);
//        // Testing addCategory
//        Category newCategory = new Category("New Category");
//        boolean added = categoryService.addCategory(newCategory);
//        System.out.println("Category added: " + added);
        // Testing getAllCategory
//        List<Category> allCategories = categoryService.getAllCategory();
//        System.out.println("All Categories: " + allCategories);
//        // Testing updateCategory
//        int categoryIdToUpdate = 1; // Assuming category with ID 2 exists
//        Category updatedCategory = new Category(1,"Updated Category");
//        boolean updated = categoryService.updateCategory(updatedCategory, categoryIdToUpdate);
//        System.out.println("Category updated: " + updated);
        // Testing deleteCategory
//        int categoryIdToDelete = 5; // Assuming category with ID 3 exists
//        boolean deleted = categoryService.deleteCategory(categoryIdToDelete);
//        System.out.println("Category deleted: " + deleted);
        System.out.println(categoryDAO.getAllCategory().stream().anyMatch(category -> category.getDescription().equalsIgnoreCase("Cakes")));
    }

}
