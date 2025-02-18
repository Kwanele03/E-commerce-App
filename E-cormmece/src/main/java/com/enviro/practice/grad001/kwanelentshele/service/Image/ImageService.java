package com.enviro.practice.grad001.kwanelentshele.service.Image;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.practice.grad001.kwanelentshele.dto.ImageDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Image;
import com.enviro.practice.grad001.kwanelentshele.model.Product;
import com.enviro.practice.grad001.kwanelentshele.repository.ImageRepository;
import com.enviro.practice.grad001.kwanelentshele.service.Product.IProductService;


@Service
public class ImageService implements IImageService {

	
	private final ImageRepository imageRepository;
	private final IProductService productServices;
	
	
	public ImageService ( ImageRepository imageRepository, IProductService productServices) {
		this.imageRepository = imageRepository;
		this.productServices = productServices;
		
	}
	
	
	@Override
	public Image getImageById(Long id) {
		// TODO Auto-generated method stub
		return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found!" + id));
	}

	@Override
	public void deleteImageById(Long id) {
		
		imageRepository.findById(id).ifPresentOrElse(imageRepository :: delete, 
				() -> { throw new ResourceNotFoundException("Image not found!" + id);		
					
				});
		
	}

	@Override
	public List<ImageDto> saveImages( List<MultipartFile> files, Long productId) {
		
		Product product = productServices.getProductById(productId);
		List<ImageDto> savedImageDtos = new ArrayList<>();
		
		for(MultipartFile file : files) {
			
			try {
				
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				String buildDownloadUrl = "/api/v1/images/image/download";
				String dowmloadUrl = buildDownloadUrl + image.getId();
				image.setDownloadUrl(dowmloadUrl);
				Image savedImage =	imageRepository.save(image);
				savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
				
				ImageDto imageDto = new ImageDto();
				imageDto.setImageId(savedImage.getId());
				imageDto.setImageName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				savedImageDtos.add(imageDto);
			
				
			} catch(IOException | SQLException exception) {
				throw new RuntimeException(exception.getMessage());
			}
		}
		return savedImageDtos;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
	
		Image image = getImageById(imageId);
		
		
		try {
		image.setFileName(file.getOriginalFilename());
		image.setFileName(file.getOriginalFilename());
		image.setImage(new SerialBlob(file.getBytes()));
		imageRepository.save(image);
		
		}
		catch (IOException | SQLException exception) {
			throw new RuntimeException(exception.getMessage());
		}
	}

}
