package com.enviro.practice.grad001.kwanelentshele.service.Category;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.enviro.practice.grad001.kwanelentshele.exceptions.AlreadyExistException;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Category;
import com.enviro.practice.grad001.kwanelentshele.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
	
	private final  CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category cannot be found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName())) 
		.map(categoryRepository :: save)
		.orElseThrow(() -> new AlreadyExistException(category.getName() + "Category is already exist!"));
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id))
		.map(oldCategory -> { oldCategory.setName(category.getName());
		return categoryRepository.save(oldCategory);	
		}) .orElseThrow(() ->  new ResourceNotFoundException("Category cannot be found"));	
		
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.findById(id)
		.ifPresentOrElse(categoryRepository :: delete, () -> {
		throw new ResourceNotFoundException("Category cannot be found");
			
		});
	}
		
}
