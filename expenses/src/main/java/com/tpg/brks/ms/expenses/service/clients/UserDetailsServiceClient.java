package com.tpg.brks.ms.expenses.service.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Service
public class UserDetailsServiceClient implements UserDetailsService {

    private final UserDetailsServiceClientConfiguration userDetailsServiceClientConfiguration;

    private final RestTemplate restTemplate;

    @Autowired
    public UserDetailsServiceClient(UserDetailsServiceClientConfiguration userDetailsServiceClientConfiguration,
                                    RestTemplateBuilder restTemplateBuilder) {

        this.userDetailsServiceClientConfiguration = userDetailsServiceClientConfiguration;

        restTemplate = restTemplateBuilder
                .rootUri(userDetailsServiceClientConfiguration.getRootUri())
                .setConnectTimeout(userDetailsServiceClientConfiguration.getConnectionTimeout())
                .setReadTimeout(userDetailsServiceClientConfiguration.getReadTimeout()).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String uri = format("%s/%s", userDetailsServiceClientConfiguration.getRootUri(), username);

        ResponseEntity<UserAuthenticated> result = restTemplate.getForEntity(uri, UserAuthenticated.class);

        return result.getBody();
    }
}
