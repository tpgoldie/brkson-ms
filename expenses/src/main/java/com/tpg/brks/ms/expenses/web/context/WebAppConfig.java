package com.tpg.brks.ms.expenses.web.context;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;

import java.util.EnumSet;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = {HAL})
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Bean
    public FilterRegistrationBean filterRegistrationBean()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HiddenHttpMethodFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/*");
        return registration;
    }
}
