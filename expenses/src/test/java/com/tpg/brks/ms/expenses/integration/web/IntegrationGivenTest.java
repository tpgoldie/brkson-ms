package com.tpg.brks.ms.expenses.integration.web;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Given;
import com.tpg.brks.ms.expenses.persistence.AccountIntegrationGiven;
import com.tpg.brks.ms.expenses.persistence.AssignmentIntegrationGiven;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.service.converters.AccountConverter;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"int"})
@Import(value = {AccountConverter.class, AccountIntegrationGiven.class, AssignmentIntegrationGiven.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {IntegrationGivenTest.TestConfig.class}, properties = {"spring.session.store-type=NONE"})
public abstract class IntegrationGivenTest implements Given, WebApplicationUserFixture {

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

    @Autowired
    protected AccountIntegrationGiven accountIntegrationGiven;

    @Autowired
    protected AssignmentIntegrationGiven assignmentIntegrationGiven;

    @MockBean
    protected AccountQueryService accountQueryService;

    @MockBean
    protected ExpenseReportQueryService expenseReportQueryService;

    @MockBean
    protected ExpenseQueryService expenseQueryService;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Before
    public void setUp() {
        initMocks(this);
    }

    protected WebApplicationUser givenAWebApplicationUser() {

        return johnDoeWebAppUser();
    }
}
