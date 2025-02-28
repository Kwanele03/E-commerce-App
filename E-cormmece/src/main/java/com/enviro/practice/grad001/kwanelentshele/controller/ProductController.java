package com.enviro.practice.grad001.kwanelentshele.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.enviro.practice.grad001.kwanelentshele.dto.ProductDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Product;
import com.enviro.practice.grad001.kwanelentshele.request.AddProductRequest;
import com.enviro.practice.grad001.kwanelentshele.request.ProductUpdateRequest;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.Product.IProductService;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	private static final HttpStatusCode INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
	private static final HttpStatusCode NOT_FOUND = HttpStatus.NOT_FOUND;

	private final IProductService productService;
	
	public ProductController(IProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<APIResponse> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
		return ResponseEntity.ok(new APIResponse("Below are all products that we currently have!", convertedProducts));
	}
	
	@GetMapping("/product/{productId}/product")
	public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId){
		try {
			Product products = productService.getProductById(productId);
			ProductDto productDto = productService.convertToDto(products);
			return ResponseEntity.ok(new APIResponse("Product Id success!" , productDto));
		} 
		catch (ResourceNotFoundException exception) {
           return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product theProduct = productService.addProduct(product);
			return ResponseEntity.ok(new APIResponse("Product added sucessfully!", theProduct));
		} 
		catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
		
	}
	
	
	@PutMapping("/product/{productId}/update")
	public ResponseEntity<APIResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
		try {
			Product product = productService.updateProduct(request, productId);
			return ResponseEntity.ok(new APIResponse ("Product Updated successfully", product));
		} 
		catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));	
		}
	}
	
	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new APIResponse("Product deleted Successfully!", productId));
		} 
		catch (ResourceNotFoundException exception) {
		    return ResponseEntity.status(NOT_FOUND).body(new APIResponse (exception.getMessage(), null));
		}
	}
	
	@GetMapping("/products/by/brand-and-name")
	public ResponseEntity<APIResponse> getProductsByNameAndBrand(@RequestParam String name, @RequestParam String brand){
		try {
			List<Product> products = productService.getProductsByNameAndBrand(name, brand);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found for that name or brand!", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new APIResponse ("Below are products for your search!", convertedProducts));
		}
		catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
	}
	
	@GetMapping("/products/by/category-and-brand")
	public ResponseEntity<APIResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found for that category or brand!", null));
			}
			return ResponseEntity.ok(new APIResponse ("Below are products for your search!", products));
		}
		catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
	}
	
	@GetMapping("/products/{name}/products")
	public ResponseEntity<APIResponse> getProductsByName(@PathVariable String name){
		try {
		List<Product> product = productService.getProductsByName(name);
		if(product.isEmpty()) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found for that name!", null));
		}
		return ResponseEntity.ok(new APIResponse ("Below are products for that name!", product));
		} 
		catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}	
	}
	
	@GetMapping("/product/by-brand")
	public ResponseEntity<APIResponse> getProductsByBrand(@RequestParam String brand){
		try {
		List<Product> product = productService.getProductsByBrand(brand);
		if(product.isEmpty()) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found for that brand!", product));
		}
		return ResponseEntity.ok(new APIResponse ("Below are products for that brand!", product));
		} 
		catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}	
	}
	
	@GetMapping("/product/{category}/all/products")
	public ResponseEntity<APIResponse> getProductsByCategory(@PathVariable String category){
		try {
		List<Product> product = productService.getProductsByCategory(category);
		if(product.isEmpty()) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found for that category!", null));
		}
		return ResponseEntity.ok(new APIResponse ("Below are products for that category!", product));
		} 
		catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
			
	}
	
	@GetMapping("/products/count/by-brand-and-name")
	public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new APIResponse("Success! Below is the number of product with that brand and name!", productCount));
		} 
		catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
	}

}
