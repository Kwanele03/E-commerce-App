package com.enviro.practice.grad001.kwanelentshele.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class shopConfig {
	
	@Bean
	ModelMapper modelMapper () {
		return new ModelMapper();
	}

}
