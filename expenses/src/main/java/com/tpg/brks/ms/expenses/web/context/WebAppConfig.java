package com.tpg.brks.ms.expenses.web.context;


import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = {HAL})
public class WebAppConfig extends WebMvcConfigurerAdapter {
}
