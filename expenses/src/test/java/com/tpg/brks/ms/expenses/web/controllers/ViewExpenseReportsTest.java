package com.tpg.brks.ms.expenses.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.web.BaseGivenTest;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;

import lombok.Value;

@RunWith(SpringRunner.class)
public class ViewExpenseReportsTest extends BaseGivenTest implements WebApplicationUserFixture {

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Captor
    private ArgumentCaptor<Assignment> assignmentArgumentCaptor;

    @Test
    @WithUserDetails(value = "jdoe")
    public void handleViewExpensesRequest_getRequest_shouldReturnExpenseReportSummary() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(13, 5, 2016);
        Date periodEnd = generateDate(15, 8, 2016);
        Period period = new Period("13/05/2016", "15/08/2016");

        List<ExpenseReport> expenseReports = givenExpenseReports(assignment, periodStart, periodEnd);

        ResultActions resultActions = whenSendRequestForViewingExpenseReports(webApplicationUser, account, assignment, expenseReports);

        thenExpectExpenseReportSummary(resultActions, webApplicationUser, account, assignment, period, expenseReports);
    }

    private ResultActions whenSendRequestForViewingExpenseReports(WebApplicationUser webApplicationUser,
                                                                  Account account,
                                                                  Assignment assignment,
                                                                  List<ExpenseReport> expenseReports) throws Exception {

        when(accountQueryService.findAccountByUsername(account.getUsername())).thenReturn(Optional.of(account));

        when(assignmentQueryService.findCurrentAssignmentForAccount(isA(Account.class))).thenReturn(Optional.of(assignment));

        when(expenseReportQueryService.getExpenseReportsForAssignment(assignment.getId())).thenReturn(expenseReports);

        return mockMvc.perform(get("/expenseReports")
                .with(user(webApplicationUser))
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    private void thenExpectExpenseReportSummary(ResultActions resultActions, WebApplicationUser webApplicationUser,
                                                Account expectedAccount,
                                                Assignment expectedAssignment,
                                                Period period,
                                                List<ExpenseReport> expenseReports) throws Exception {

        resultActions
                .andExpect(status().isOk())
                .andExpect(authenticated()
                    .withUsername(webApplicationUser.getUsername())
                    .withRoles("EXPENSE_USER"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].expenseReportId", is(expenseReports.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].description", is(expenseReports.get(0).getDescription())))
                .andExpect(jsonPath("$[0].periodStart", is(period.getPeriodStart())))
                .andExpect(jsonPath("$[0].periodEnd", is(period.getPeriodEnd())))
                .andExpect(jsonPath("$[0].status", is(expenseReports.get(0).getStatus().name())));

        verifyAccountQuery(expectedAccount);

        verify(accountQueryService).findAccountByUsername(webApplicationUser.getUsername());

        verifyAssignmentQuery(expectedAssignment);
    }

    private void verifyAccountQuery(Account expectedAccount) {
        verify(assignmentQueryService).findCurrentAssignmentForAccount(accountArgumentCaptor.capture());

        Account actualAccount = accountArgumentCaptor.getValue();

        assertThat(actualAccount, is(expectedAccount));
    }

    private void verifyAssignmentQuery(Assignment expectedAssignment) {
        verify(expenseReportQueryService).getExpenseReportsForAssignment(expectedAssignment.getId());
    }

    @Value
    private class Period {
        private final String periodStart;
        private final String periodEnd;

        Period(String periodStart, String periodEnd) {
            this.periodStart = periodStart;
            this.periodEnd = periodEnd;
        }
    }
}
