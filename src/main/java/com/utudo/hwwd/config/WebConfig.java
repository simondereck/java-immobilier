package com.utudo.hwwd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/users/**/**").addResourceLocations("file:/Library/WebServer/Documents/imgs/users/");
//        registry.addResourceHandler("/partners/**/**").addResourceLocations("file:/Library/WebServer/Documents/imgs/partners/");
//        registry.addResourceHandler("/admins/**/**").addResourceLocations("file:/Library/WebServer/Documents/admins/");



        registry.addResourceHandler("/admins/**/**").addResourceLocations("file:/var/www/html/admins/");
        registry.addResourceHandler("/partners/**/**").addResourceLocations("file:/var/www/html/imgs/partners/");
        registry.addResourceHandler("/users/**/**").addResourceLocations("file:/var/www/html/imgs/users/");
        registry.addResourceHandler("/cobbers/**").addResourceLocations("file:/var/www/html/imgs/cobbers/");


        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
