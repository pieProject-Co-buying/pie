package com.pie.pieProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	public String uploaddir = "file:/"+System.getProperty("user.dir")
			+ "/src/main/resources/static/imgs/test/";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/imgs/test/**")
				.addResourceLocations(uploaddir);
	} 

}
