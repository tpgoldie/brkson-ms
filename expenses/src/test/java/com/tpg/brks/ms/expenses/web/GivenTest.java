package com.tpg.brks.ms.expenses.web;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.Given;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {GivenTest.TestConfig.class})
@ActiveProfiles(profiles = {"dev"})
public abstract class GivenTest implements Given, WebApplicationUserFixture {

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

    protected void verifyAccountQuery(Account expectedAccount) {
        verify(assignmentQueryService).findCurrentAssignmentForAccount(accountArgumentCaptor.capture());

        Account actualAccount = accountArgumentCaptor.getValue();

        assertThat(actualAccount, is(expectedAccount));
    }

    protected void verifyAssignmentQuery(Assignment expectedAssignment) {
        verify(expenseReportQueryService).getExpenseReportsForAssignment(expectedAssignment.getId());
    }
}
