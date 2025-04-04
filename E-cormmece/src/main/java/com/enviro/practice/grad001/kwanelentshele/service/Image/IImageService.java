package com.enviro.practice.grad001.kwanelentshele.service.Image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.enviro.practice.grad001.kwanelentshele.dto.ImageDto;
import com.enviro.practice.grad001.kwanelentshele.model.Image;

public interface IImageService {

	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
	void updateImage(MultipartFile file, Long imageId);
}
