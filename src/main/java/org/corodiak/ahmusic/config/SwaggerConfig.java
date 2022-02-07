package org.corodiak.ahmusic.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName("V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.corodiak.ahmusic"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo("아뭐듣지 API Docs", "V2"));
	}
	
	private ApiInfo apiInfo(String title, String version) {
		return new ApiInfo(title,
				"아뭐듣지 HTTP API - 모바일프로그래밍",
				version,
				"corodiak.org",
				new Contact("Contact Me", "corodiak.org", "sdst74@kyonggi.ac.kr"),
				"Licenses",
				"corodiak.org",
				new ArrayList<>());
	}
}
