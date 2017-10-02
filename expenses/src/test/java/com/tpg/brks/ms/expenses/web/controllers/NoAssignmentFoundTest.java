package com.tpg.brks.ms.expenses.web.controllers;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.web.BaseGivenTest;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {BaseGivenTest.TestConfig.class}, properties = {"spring.session.store-type=NONE"})
public class NoAssignmentFoundTest extends BaseGivenTest {

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Test
    @WithUserDetails(value = "jdoe")
    public void handleViewExpensesRequest_getRequest_shouldReturnExpenseReportSummary() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Account account = givenAnAccount();
        
        ResultActions resultActions = whenSendRequestForViewingExpenseReports(webApplicationUser, account);

        thenResponseIsNotFound(resultActions, webApplicationUser, account);
    }

    private ResultActions whenSendRequestForViewingExpenseReports(WebApplicationUser webApplicationUser, 
    		Account account) throws Exception {
        when(accountQueryService.findAccountByUsername(webApplicationUser.getUsername())).thenReturn(Optional.of(account));

    	when(assignmentQueryService.findCurrentAssignmentForAccount(isA(Account.class))).thenReturn(empty());

        return mockMvc.perform(get("/expenseReports")
                .with(user(webApplicationUser))
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    private void thenResponseIsNotFound(ResultActions resultActions, WebApplicationUser webApplicationUser,
                                        Account expectedAccount) throws Exception {
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(authenticated()
                .withUsername(webApplicationUser.getUsername())
                .withRoles("EXPENSE_USER"));

        verify(accountQueryService).findAccountByUsername(webApplicationUser.getUsername());
        verify(assignmentQueryService).findCurrentAssignmentForAccount(accountArgumentCaptor.capture());

        Account actualAccount = accountArgumentCaptor.getValue();

        assertThat(actualAccount, hasProperty("username", is(expectedAccount.getUsername())));
        assertThat(actualAccount, hasProperty("firstName", is(expectedAccount.getFirstName())));
        assertThat(actualAccount, hasProperty("lastName", is(expectedAccount.getLastName())));
    }
   
}
