package com.tpg.brks.ms.expenses.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.domain.*;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import org.junit.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetExpenseReportTest extends ExpenseReportControllerTest {

    @Test
    @WithUserDetails(value = "jdoe")
    public void getExpenseReport_getRequest_shouldReturnExpenseReport() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(13, 5, 2016);
        Date periodEnd = generateDate(15, 8, 2016);
        Period period = new Period("13/05/2016", "15/08/2016");

        ExpenseReport expenseReport = givenAPendingExpenseReport(assignment, periodStart, periodEnd);

        ResultActions resultActions = whenGettingExpenseReport(webApplicationUser, expenseReport);

        thenExpectExpenseReport(resultActions, webApplicationUser, period, expenseReport);
    }

    private ResultActions whenGettingExpenseReport(WebApplicationUser webApplicationUser,
                                                   ExpenseReport expenseReport) throws Exception {

        when(expenseReportQueryService.getExpenseReport(expenseReport.getId())).thenReturn(of(expenseReport));

        return mockMvc.perform(get("/expenseReports/{reportId}", expenseReport.getId())
                .with(user(webApplicationUser))
            .contentType(HAL_JSON))
            .andDo(print());
    }

    private void thenExpectExpenseReport(ResultActions resultActions, WebApplicationUser webApplicationUser,
                                                Period period,
                                                ExpenseReport expenseReport) throws Exception {

        Expense actualExpense = expenseReport.getExpenses().get(0);

        resultActions
            .andExpect(status().isOk())
            .andExpect(authenticated()
                .withUsername(webApplicationUser.getUsername())
                .withRoles("EXPENSE_USER"))
            .andExpect(jsonPath("$.id", is(expenseReport.getId().intValue())))
            .andExpect(jsonPath("$.description", is(expenseReport.getDescription())))
            .andExpect(jsonPath("$.periodStart", is(period.getPeriodStart())))
            .andExpect(jsonPath("$.periodEnd", is(period.getPeriodEnd())))
            .andExpect(jsonPath("$.status", is(expenseReport.getStatus().name())))
            .andExpect(jsonPath("$.expenses", hasSize(1)))
            .andExpect(jsonPath("$.expenses[0].id", is(actualExpense.getId().intValue())))
            .andExpect(jsonPath("$.expenses[0].description", is(actualExpense.getDescription())))
            .andExpect(jsonPath("$.expenses[0].amount", is(actualExpense.getAmount().doubleValue())));

        verify(expenseReportQueryService).getExpenseReport(expenseReport.getId());
    }
}
