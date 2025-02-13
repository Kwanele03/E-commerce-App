package com.enviro.practice.grad001.kwanelentshele.service.Product;

import java.util.List;

import com.enviro.practice.grad001.kwanelentshele.Model.Category;

public interface ICategoryService {
	
	
	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();
	Category addCategory(Category category);
	Category updateCategory(Category category, Long id);
	void deleteCategory(Long id);
	
	
	


}
