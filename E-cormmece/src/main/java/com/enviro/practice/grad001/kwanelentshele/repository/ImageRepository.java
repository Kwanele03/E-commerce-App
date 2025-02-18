package com.enviro.practice.grad001.kwanelentshele.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.practice.grad001.kwanelentshele.Model.Image;
import com.enviro.practice.grad001.kwanelentshele.Model.Product;

public interface ImageRepository extends JpaRepository<Image, Long>{

	List<Image> findByProductId(Long id);

}
