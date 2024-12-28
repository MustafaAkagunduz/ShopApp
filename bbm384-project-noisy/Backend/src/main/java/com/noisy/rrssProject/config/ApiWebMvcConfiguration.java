package com.noisy.rrssProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import java.util.Collections;
import java.util.List;

// https://github.com/springdoc/springdoc-openapi/issues/2143 SwaggerUI Integration with Spring Security Problem Solved!
@Configuration
public class ApiWebMvcConfiguration extends DelegatingWebMvcConfiguration {


    private final Jackson2ObjectMapperBuilder jacksonBuilder;

    public ApiWebMvcConfiguration(Jackson2ObjectMapperBuilder jacksonBuilder) {
        this.jacksonBuilder = jacksonBuilder;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var applicationContext = this.getApplicationContext();
        if (applicationContext != null) {
            jacksonBuilder.applicationContext(applicationContext);
        }
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(jacksonBuilder.build()));

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(List.of(
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_HTML,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/openmetrics-text;version=1.0.0;charset=utf-8")
        ));
        converters.add(stringConverter);
    }
}