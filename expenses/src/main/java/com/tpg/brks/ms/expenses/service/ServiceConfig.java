package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.service.clients.UserDetailsServiceClient;
import com.tpg.brks.ms.expenses.service.clients.UserDetailsServiceClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

public class ServiceConfig {

    @Autowired
    private UserDetailsServiceClientConfiguration configuration;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceClient(configuration, restTemplateBuilder);
    }
}
