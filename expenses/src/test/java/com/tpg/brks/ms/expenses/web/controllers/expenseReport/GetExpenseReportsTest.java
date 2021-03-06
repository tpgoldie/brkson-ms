package com.tpg.brks.ms.expenses.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.domain.Period;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import org.junit.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetExpenseReportsTest extends ExpenseReportControllerTest {

    @Test
    @WithUserDetails(value = "jdoe")
    public void getExpenseReports_getRequest_shouldReturnExpenses() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(13, 5, 2016);
        Date periodEnd = generateDate(15, 8, 2016);
        Period period = new Period("13/05/2016", "15/08/2016");

        List<ExpenseReport> expenseReports = singletonList(givenAPendingExpenseReport(assignment, periodStart, periodEnd));

        ResultActions resultActions = whenSendRequestForViewingExpenseReports(webApplicationUser, account, assignment, expenseReports);

        thenExpectExpenseReports(resultActions, webApplicationUser, account, assignment, period, expenseReports);
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
            .contentType(HAL_JSON_VALUE))
                .andDo(print());
    }

    private void thenExpectExpenseReports(ResultActions resultActions, WebApplicationUser webApplicationUser,
                                                Account expectedAccount,
                                                Assignment expectedAssignment,
                                                Period period,
                                                List<ExpenseReport> expenseReports) throws Exception {

        Long expenseReportId = expenseReports.get(0).getId();
        Long expenseId = expenseReports.get(0).getExpenses().get(0).getId();

        resultActions
                .andExpect(status().isOk())
                .andExpect(authenticated()
                    .withUsername(webApplicationUser.getUsername())
                    .withRoles("EXPENSE_USER"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(expenseReports.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].description", is(expenseReports.get(0).getDescription())))
                .andExpect(jsonPath("$[0].periodStart", is(period.getPeriodStart())))
                .andExpect(jsonPath("$[0].periodEnd", is(period.getPeriodEnd())))
                .andExpect(jsonPath("$[0].status", is(expenseReports.get(0).getStatus().name())))
                .andExpect(jsonPath("$[0].links[0].href", containsString(String.format("expenseReports/%s", expenseReportId))))
                .andExpect(jsonPath("$[0].expenses", hasSize(1)))
                .andExpect(jsonPath("$[0].expenses[0].id", is(expenseId.intValue())));

        verifyAccountQuery(expectedAccount);

        verify(accountQueryService).findAccountByUsername(webApplicationUser.getUsername());

        verifyAssignmentQuery(expectedAssignment);
    }
}
