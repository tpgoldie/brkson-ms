package com.tpg.brks.ms.expenses.service.clients;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ConfigurationProperties(prefix = "user.details.service.client")
public class UserDetailsServiceClientProperties implements UserDetailsServiceClientConfiguration {
    @NotNull
    private String rootUri;

    @Min(value = 1000)
    private int connectionTimeout;

    @Min(value = 1000)
    private int readTimeout;
}
