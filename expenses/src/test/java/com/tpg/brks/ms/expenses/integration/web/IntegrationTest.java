package com.tpg.brks.ms.expenses.integration.web;

import com.tpg.brks.ms.expenses.ExpensesMsApplication;
import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Given;
import com.tpg.brks.ms.expenses.persistence.integration.AccountIntegrationGiven;
import com.tpg.brks.ms.expenses.persistence.integration.AssignmentIntegrationGiven;
import com.tpg.brks.ms.expenses.persistence.integration.ExpenseReportIntegrationGiven;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseQueryService;
import com.tpg.brks.ms.expenses.service.ServiceConfig;
import com.tpg.brks.ms.expenses.service.converters.AccountConverter;
import com.tpg.brks.ms.expenses.web.context.WebAppSecurityConfig;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {"int"})
@Import(value = {AccountConverter.class, AccountIntegrationGiven.class, AssignmentIntegrationGiven.class, ExpenseReportIntegrationGiven.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {ServiceConfig.class, WebAppSecurityConfig.class, ExpensesMsApplication.class}, properties = {"spring.session.store-type=NONE"})
public abstract class IntegrationTest implements WebApplicationUserFixture {

//    @TestConfiguration
//    public static class TestConfig implements WebApplicationUserFixture {
//        private UserDetailsService userDetailsService = new UserDetailsService() {
//            private Map<String, UserDetails> users = Stream.of(johnDoeWebAppUser())
//                    .collect(toMap(WebApplicationUser::getUsername, user -> user));
//
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return users.get(username);
//            }
//        };
//
//        @Bean
//        public UserDetailsService userDetailsService() { return userDetailsService; }
//    }

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private MockRestServiceServer server;

    protected HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    protected AccountIntegrationGiven accountIntegrationGiven;

    @Autowired
    protected AssignmentIntegrationGiven assignmentIntegrationGiven;

    @Autowired
    protected ExpenseReportIntegrationGiven expenseReportIntegrationGiven;

    @MockBean
    protected AccountQueryService accountQueryService;

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

    protected String createUrl(String uri) {
        return String.format("http://localhost:%d%s", port, uri);
    }
}
