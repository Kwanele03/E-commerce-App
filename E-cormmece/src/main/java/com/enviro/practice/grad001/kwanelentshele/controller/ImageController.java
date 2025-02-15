Ipackage com.enviro.practice.grad001.kwanelentshele.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.practice.grad001.kwanelentshele.Model.Image;
import com.enviro.practice.grad001.kwanelentshele.dto.ImageDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.Image.IImageService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	

private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
	
	private final IImageService imageServices;
	
	
	public ImageController(IImageService imageServices ) {
		this.imageServices = imageServices;
	
	}
	
	@PostMapping("/upload")
	public ResponseEntity<APIResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
		
		try {List<ImageDto> imageDtos = imageServices.saveImages(files, productId);
		
		return ResponseEntity.ok(new APIResponse( "Updload Success", imageDtos));
		
	} catch(Exception exception){
		
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse ("Upload failed!", exception.getMessage()));
	}
		
  }
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException, IOException {
	    Image image = imageServices.getImageById(imageId);

	    // Assuming image.getImage() returns a Blob
	    Blob imageBlob = image.getImage();
	    byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
	    ByteArrayResource resource = new ByteArrayResource(imageBytes);

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(image.getFileType()))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
	            .body(resource);
	}
	
	
	@PutMapping ("/image/{}/update")
	public ResponseEntity<APIResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
		
		Image image = imageServices.getImageById(imageId);
	try {
		if(image == null) {
			imageServices.updateImage(file, imageId);
		}
		
		return ResponseEntity.ok(new APIResponse( "Update Success", null));
	}
    
	catch(ResourceNotFoundException exception) {
		return ResponseEntity.status(NOT_FOUND).body(new APIResponse (exception.getMessage(),null));
	}
	
	return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse ("Update failed!", INTERNAL_SERVER_ERROR));
	
	}
	
	
	@DeleteMapping ("/image/{}/delete")
	public ResponseEntity<APIResponse> deleteImage(@PathVariable Long imageId){
		
		Image image = imageServices.getImageById(imageId);
	try {
		if(image == null) {
			imageServices.deleteImageById(imageId);
		}
		
		return ResponseEntity.ok(new APIResponse( "Delete Success", null));
	}
    
	catch(ResourceNotFoundException exception) {
		return ResponseEntity.status(NOT_FOUND).body(new APIResponse (exception.getMessage(),null));
	}
	
	return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse ("Delete failed!", INTERNAL_SERVER_ERROR));
	
	}
	
	
}
