package com.enviro.practice.grad001.kwanelentshele.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enviro.practice.grad001.kwanelentshele.exceptions.AlreadyExistException;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Category;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.Category.ICategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	
	private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
	private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
	private static final HttpStatusCode CONFLICT = HttpStatus.CONFLICT;

	private final ICategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<APIResponse> getAllCategories(){
	try {	
		 List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(new APIResponse("Here are all categories!", categories));
	}
	catch(Exception exception) {
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Erro:", INTERNAL_SERVER_ERROR));	
	}
  }
	@PostMapping("/add")
	public ResponseEntity<APIResponse> addCategory(@RequestBody Category name){
		try {
		     Category theCategory = categoryService.addCategory(name);
		return ResponseEntity.ok(new APIResponse("Category added successfully!", theCategory));
	   } 
		catch (AlreadyExistException exception) {
			return ResponseEntity.status(CONFLICT).body(new APIResponse(exception.getMessage(), null));
	   }
	}
	
	@GetMapping("/category/{id}/category-id")
	public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id){
		try {
	         Category theCategory = categoryService.getCategoryById(id);
		   return  ResponseEntity.ok(new APIResponse("Success, here is category with that id!", theCategory));
		}
		catch (ResourceNotFoundException notFound){
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse(notFound.getMessage(), null));	
		}
	}
   
	
	@GetMapping("/category/{name}/category-name")
	public ResponseEntity<APIResponse> getCategoryByName(@PathVariable("name") String name){
		try {
		     Category theCategory = categoryService.getCategoryByName(name);
		return  ResponseEntity.ok(new APIResponse("Success, here are categories with that name!", theCategory));
		}
		catch (ResourceNotFoundException notFound){
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse(notFound.getMessage(), null));	
		}
	}
	
	@DeleteMapping("/category/{id}/delete")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id){
		try {
             categoryService.deleteCategory(id);
		return  ResponseEntity.ok(new APIResponse("Category deleted successfully!", null));
		}
		catch (ResourceNotFoundException notFound){
			return ResponseEntity.status(NOT_FOUND).body(new APIResponse(notFound.getMessage(), null));	
		}
	}
	
	@PutMapping ("category/{id}/update")
	public ResponseEntity<APIResponse> updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        try {
        	Category updateCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new APIResponse("Update was successful!", updateCategory));
        } 
		catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
        }
	}	
	
}
