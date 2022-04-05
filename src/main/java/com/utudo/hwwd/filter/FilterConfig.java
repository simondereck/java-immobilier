package com.utudo.hwwd.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> registFilter() {
        FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/user/*");
        registration.setName("LoginFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<PartnerFilter> partnerBean(){
        FilterRegistrationBean<PartnerFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new PartnerFilter());
        registration.addUrlPatterns("/partner/*");
        registration.setName("PartnerFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter(){
        FilterRegistrationBean<AdminFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AdminFilter());
        registration.addUrlPatterns("/admin/*");
        registration.setName("AdminFilter");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<PartnerBussinessFilter> partnerBussinessFilter(){
        FilterRegistrationBean<PartnerBussinessFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new PartnerBussinessFilter());
        registration.addUrlPatterns("/partner/bussiness/*");
        registration.setName("PartnerBussinessFilter");
        registration.setOrder(4);
        return registration;
    }
}
