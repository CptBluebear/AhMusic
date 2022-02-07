package org.corodiak.ahmusic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${resources.music.location}")
	private String resourceMusicLocation;
	
	@Value("${resources.music.uripath}")
	private String resourceMusicUriPath;
	
	@Value("${resources.thumbs.location}")
	private String resourceThumbsLocation;
	
	@Value("${resources.thumbs.uripath}")
	private String resourceThumbsUriPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
			.addResourceHandler(resourceMusicUriPath + "/**")
			.addResourceLocations("file://" + resourceMusicLocation);

		registry
			.addResourceHandler(resourceThumbsUriPath + "/**")
			.addResourceLocations("file://" + resourceThumbsLocation);
		
		registry.
        addResourceHandler("/documentation/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		
		registry.
		addResourceHandler("/documentation/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs?group=V2");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/security","/swagger-resources/configuration/security");
        registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
	}
}
