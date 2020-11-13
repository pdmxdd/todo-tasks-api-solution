package org.launchcode.todo;

import java.util.function.Predicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Bean
	public Docket todoApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(Predicate.not(PathSelectors.regex("/error.*")))
			.build()
			.apiInfo(todoApiInfo());
	}

	private ApiInfo todoApiInfo() {
		return new ApiInfoBuilder()
			.title("Todo Tasks API")
			.description("An API for creating Todo items and associated subtasks")
			.contact(new Contact("Paul Matthews", "paul@launchcode.org", "paul@launchcode.org"))
			.license("MIT")
			.licenseUrl("https://opensource.org/licenses/MIT")
			.build();
	}
}
