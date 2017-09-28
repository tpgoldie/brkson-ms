package com.tpg.brks.ms.expenses.web;

import com.tpg.brks.ms.expenses.domain.*;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {BaseGivenTest.TestConfig.class}, properties = {"spring.session.store-type=NONE"})
public abstract class BaseGivenTest implements Given, WebApplicationUserFixture {

    @TestConfiguration
    public static class TestConfig implements WebApplicationUserFixture {
        private UserDetailsService userDetailsService = new UserDetailsService() {
            private Map<String, UserDetails> users = singletonList(johnDoeWebAppUser()).stream()
                    .collect(toMap(WebApplicationUser::getUsername, user -> user));

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return users.get(username);
            }
        };

        @Bean
        public UserDetailsService userDetailsService() { return userDetailsService; }
    }

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AssignmentQueryService assignmentQueryService;

    @MockBean
    protected AccountQueryService accountQueryService;

    @MockBean
    protected ExpenseReportQueryService expenseReportQueryService;

    protected WebApplicationUser givenAWebApplicationUser() {
        return johnDoeWebAppUser();
    }
}
