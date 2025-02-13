package com.enviro.practice.grad001.kwanelentshele.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviro.practice.grad001.kwanelentshele.Model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCategoryName(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByCategoryNameAndBrand(String category, String brand);

	List<Product> findByName(String name);

	List<Product> findByNameAndBrand(String name, String brand);

	Long countByBrandAndName(String brand, String name);

	

}
