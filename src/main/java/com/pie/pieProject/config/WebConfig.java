package com.pie.pieProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/imgs/test/**")
				.addResourceLocations("file:/C:/Users/KDG/git/pie/src/main/resources/static/imgs/test/");
		// TODO Auto-generated method stub
	}

}
