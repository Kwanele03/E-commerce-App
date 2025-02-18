package com.enviro.practice.grad001.kwanelentshele.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.practice.grad001.kwanelentshele.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

	List<Image> findByProductId(Long id);
}
