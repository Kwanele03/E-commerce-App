package com.enviro.practice.grad001.kwanelentshele.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enviro.practice.grad001.kwanelentshele.Model.Product;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
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
		return ResponseEntity.ok(new APIResponse("Success 1", products));
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId){
		try {
			Product products = productService.getProductById(productId);
			return ResponseEntity.ok(new APIResponse("Product Id success!" , products));
		} catch (ResourceNotFoundException exception) {
           return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product){
		
		try {
			Product theProduct = productService.addProduct(product);
			
			return ResponseEntity.ok(new APIResponse("Success", theProduct));
		} catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
		
	}
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<APIResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
		
		try {
			Product product = productService.updateProduct(request, productId);
			return ResponseEntity.ok(new APIResponse ("Product Updated successfully", product));
		} catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
			
		}
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new APIResponse("Product deleted Successfully!", productId));
		} catch (ResourceNotFoundException exception) {
		 return ResponseEntity.status(NOT_FOUND).body(new APIResponse (exception.getMessage(), null));
		}
	}
	
	@GetMapping("/by/brand-and-name")
	public ResponseEntity<APIResponse> getProductsByNameAndBrand(@RequestParam String name, @RequestParam String brand){
		try {
			List<Product> products = productService.getProductsByNameAndBrand(name, brand);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found!", null));
			}
			return ResponseEntity.ok(new APIResponse ("Sucess", products));
		}  catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		
		}
	}
	
	@GetMapping("by/category-and-brand")
	public ResponseEntity<APIResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
		
		try {
			List<Product> products = productService.getProductsByNameAndBrand(category, brand);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found!", null));
			}
			return ResponseEntity.ok(new APIResponse ("Sucess", products));
		}  catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		
		}
		
	}
	
	@GetMapping("/{name}/product")
	public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name){
		try {
		List<Product> product = productService.getProductsByName(name);
		if(product.isEmpty()) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found!", null));
		}
		return ResponseEntity.ok(new APIResponse ("Sucess", product));
		} catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
			
	}
	
	@GetMapping("/by-brand")
	public ResponseEntity<APIResponse> getProductsByBrand(@RequestParam String brand){
		try {
		List<Product> product = productService.getProductsByName(brand);
		if(product.isEmpty()) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found!", null));
		}
		return ResponseEntity.ok(new APIResponse ("Sucess", product));
		} catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
			
	}
	
	@GetMapping("/{category}/all/by-category")
	public ResponseEntity<APIResponse> getProductsByCategory(@PathVariable String category){
		try {
		List<Product> product = productService.getProductsByName(category);
		if(product.isEmpty()) {
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found!", null));
		}
		return ResponseEntity.ok(new APIResponse ("Sucess", product));
		} catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
		}
			
	}
	
	@GetMapping("/count/by-brand-and-name")
	public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand,
			@RequestParam String name) {
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new APIResponse("Success good!", productCount));
		} catch (Exception exception) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR)
					.body(new APIResponse(exception.getMessage(), null));
		}
	}


}
