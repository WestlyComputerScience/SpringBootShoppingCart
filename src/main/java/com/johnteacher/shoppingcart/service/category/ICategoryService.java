package com.johnteacher.shoppingcart.service.category;

import com.johnteacher.shoppingcart.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    public void deleteCategoryById(Long id);
}
