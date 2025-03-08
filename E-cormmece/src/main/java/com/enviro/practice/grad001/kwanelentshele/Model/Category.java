package com.enviro.practice.grad001.kwanelentshele.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	

	private String name;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category(){}

	public Category(String name){
		this.name = name;
	}

	public Category(Long Id, String name){
		this.Id =Id;
		this.name = name;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
