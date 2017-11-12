package com.tpg.brks.ms.expenses.service.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static com.tpg.brks.ms.expenses.service.clients.AuthenticatedUserMatcher.matchesAuthenticatedUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({UserDetailsServiceClient.class, UserDetailsServiceClientProperties.class})
public class AuthenticateUserTest {

    @Autowired
    private UserDetailsServiceClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void authenticateUser_userDetails_shouldAuthenticateUser() throws JsonProcessingException {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser("jdoe", "ABC123");

        AuthenticatedUser actual = whenAuthenticatingUser(authenticatedUser);

        assertThat(actual, matchesAuthenticatedUser(authenticatedUser));

        server.verify();
    }

    private AuthenticatedUser whenAuthenticatingUser(AuthenticatedUser authenticatedUser) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(authenticatedUser);

        server.expect(requestTo("https://localhost:8443/users/jdoe"))
                .andExpect(method(GET))
            .andRespond(withSuccess(json, APPLICATION_JSON));

        return (AuthenticatedUser) client.loadUserByUsername(authenticatedUser.getUsername());
    }
}
