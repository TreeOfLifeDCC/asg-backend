package com.asg.platform.config;

import com.asg.platform.es.mapping.SecondaryOrganism;
import com.asg.platform.es.mapping.StatusTracking;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.asg.platform.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/api")
                .apiInfo(metaData())
                .additionalModels(new TypeResolver().resolve(SecondaryOrganism.class),new TypeResolver().resolve(StatusTracking.class));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:/swagger/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Aquatic Symbiosis Project",
                "Sequencing Aquatic species of Britain and Ireland",
                "1.0",
                "Terms of service",
                "Talal Ibrahim",
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }

}