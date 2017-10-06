package com.tpg.brks.ms.expenses.web.controllers;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.domain.Period;
import com.tpg.brks.ms.expenses.web.BaseGivenTest;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ViewExpenseReportTest extends BaseGivenTest {

    @Test
    @WithUserDetails(value = "jdoe")
    public void handleViewExpensesRequest_getRequest_shouldReturnExpenseReportSummary() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(13, 5, 2016);
        Date periodEnd = generateDate(15, 8, 2016);
        Period period = new Period("13/05/2016", "15/08/2016");

        ExpenseReport expenseReport = givenAPendingExpenseReport(assignment, periodStart, periodEnd);

        ResultActions resultActions = whenSendRequestForViewingExpenseReport(webApplicationUser, account, assignment,
                expenseReport);

        thenExpectExpenseReport(resultActions, webApplicationUser, account, assignment, period, expenseReport);
    }

    private ResultActions whenSendRequestForViewingExpenseReport(WebApplicationUser webApplicationUser,
                                                                  Account account,
                                                                  Assignment assignment,
                                                                  ExpenseReport expenseReport) throws Exception {

        when(accountQueryService.findAccountByUsername(account.getUsername())).thenReturn(of(account));

        when(assignmentQueryService.findCurrentAssignmentForAccount(isA(Account.class))).thenReturn(of(assignment));

        when(expenseReportQueryService.getExpenseReport(expenseReport.getId())).thenReturn(of(expenseReport));

        return mockMvc.perform(get("/expenseReports/{reportId}", expenseReport.getId())
                .with(user(webApplicationUser))
            .contentType(HAL_JSON))
            .andDo(print());
    }

    private void thenExpectExpenseReport(ResultActions resultActions, WebApplicationUser webApplicationUser,
                                                Account expectedAccount,
                                                Assignment expectedAssignment,
                                                Period period,
                                                ExpenseReport expenseReport) throws Exception {

        resultActions
            .andExpect(status().isOk())
            .andExpect(authenticated()
                .withUsername(webApplicationUser.getUsername())
                .withRoles("EXPENSE_USER"))
            .andExpect(jsonPath("$.expenseReportId", is(expenseReport.getId().intValue())))
            .andExpect(jsonPath("$.description", is(expenseReport.getDescription())))
            .andExpect(jsonPath("$.periodStart", is(period.getPeriodStart())))
            .andExpect(jsonPath("$.periodEnd", is(period.getPeriodEnd())))
            .andExpect(jsonPath("$.status", is(expenseReport.getStatus().name())))
            .andExpect(jsonPath("$.expenses[0].id", is(expenseReport.getExpenses().get(0).getId())))
            .andExpect(jsonPath("$.expenses[0].description", is(expenseReport.getExpenses().get(0).getDescription())))
            .andExpect(jsonPath("$.expenses[0].amount", is(expenseReport.getExpenses().get(0).getAmount())));

        verifyAccountQuery(expectedAccount);

        verify(accountQueryService).findAccountByUsername(webApplicationUser.getUsername());

        verifyAssignmentQuery(expectedAssignment);
    }
}
