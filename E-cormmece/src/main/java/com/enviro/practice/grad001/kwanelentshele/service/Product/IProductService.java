package com.enviro.practice.grad001.kwanelentshele.service.Product;

import java.util.List;

import com.enviro.practice.grad001.kwanelentshele.Model.Product;
import com.enviro.practice.grad001.kwanelentshele.request.AddProductRequest;
import com.enviro.practice.grad001.kwanelentshele.request.ProductUpdateRequest;

public interface IProductService {

	
	
	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(ProductUpdateRequest request, Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName (String name);
	List<Product> getProductsByNameAndBrand(String name, String brand);
	Long countProductsByBrandAndName(String brand, String name );
}
