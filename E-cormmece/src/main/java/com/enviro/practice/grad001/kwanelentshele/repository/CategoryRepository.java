package com.enviro.practice.grad001.kwanelentshele.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.practice.grad001.kwanelentshele.model.Category;
import com.enviro.practice.grad001.kwanelentshele.request.AddProductRequest;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);
	Category findByName(AddProductRequest request);
	boolean existsByName(String name);

}
