package com.tpg.brks.ms.expenses;

import com.tpg.brks.ms.expenses.service.clients.UserDetailsServiceClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({UserDetailsServiceClientProperties.class})
public class ExpensesMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpensesMsApplication.class, args);
    }
}
