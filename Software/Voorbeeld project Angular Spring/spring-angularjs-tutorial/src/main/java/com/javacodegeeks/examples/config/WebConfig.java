package com.javacodegeeks.examples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan("com.javacodegeeks.examples")
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webapp/resources/static/js/**")
                .addResourceLocations("/webapp/resources/static/js/");
        registry.addResourceHandler("/webapp/resources/static/css/**")
                .addResourceLocations("/webapp/resources/static/css/");
        registry.addResourceHandler("/webapp/resources/static/views/**")
                .addResourceLocations("/webapp/resources/static/views/");
        registry.addResourceHandler("/webapp/resources/static/**")
                .addResourceLocations("/webapp/resources/static/");
        registry.addResourceHandler("/webapp/webjars/**")
                .addResourceLocations("/webapp/webjars/");
    }

    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
