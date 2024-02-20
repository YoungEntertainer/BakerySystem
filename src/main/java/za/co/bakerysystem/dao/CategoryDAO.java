package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.Category;


public interface CategoryDAO {

    Category getCategoryById(int categoryID);

    boolean addCategory(Category category);
    
    List<Category> getAllCategory();

    boolean updateCategory(Category category, int categoryId);

    boolean deleteCategory(int categoryID);
}
