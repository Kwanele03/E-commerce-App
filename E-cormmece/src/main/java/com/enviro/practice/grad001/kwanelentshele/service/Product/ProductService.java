package com.enviro.practice.grad001.kwanelentshele.service.Product;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.enviro.practice.grad001.kwanelentshele.dto.ImageDto;
import com.enviro.practice.grad001.kwanelentshele.dto.ProductDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ProductNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Category;
import com.enviro.practice.grad001.kwanelentshele.model.Image;
import com.enviro.practice.grad001.kwanelentshele.model.Product;
import com.enviro.practice.grad001.kwanelentshele.repository.CategoryRepository;
import com.enviro.practice.grad001.kwanelentshele.repository.ImageRepository;
import com.enviro.practice.grad001.kwanelentshele.repository.ProductRepository;
import com.enviro.practice.grad001.kwanelentshele.request.AddProductRequest;
import com.enviro.practice.grad001.kwanelentshele.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ImageRepository imageRepository;
	private final ModelMapper modelMapper;
    
	@Override
	public Product addProduct(AddProductRequest request) {
		
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getName()))
	    .orElseGet(() ->{  Category newCategory = new Category (null, request.getCategory().getName());
		return categoryRepository.save(newCategory);
		});
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}

	private Product createProduct(AddProductRequest request, Category category) {
		
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
				);	
	}
	
	@Override
	public Product getProductById(Long id) {

		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {throw  new ProductNotFoundException("Product Not Found");});
		
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
				.map(existingProduct ->  updateExistingProduct(existingProduct, request))
				.map(productRepository :: save)
				.orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
		
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
		
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByNameAndBrand(String name, String brand) {
		return productRepository.findByNameAndBrand(name, brand);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}

	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products) {
		return products.stream().map(this :: convertToDto).toList();
		
	}
	
	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDto = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
		
		productDto.setImages(imageDto);
		return productDto;
	}
}
