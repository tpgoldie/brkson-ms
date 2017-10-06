package com.tpg.brks.ms.expenses.web.controllers.expense;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.integration.web.IntegrationGivenTest;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static java.util.Optional.empty;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoExpenseFoundTest extends ExpenseControllerTest {

    @Test
    @WithUserDetails(value = "jdoe")
    public void getExpense_getRequest_shouldReturnNotFoundStatus() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(13, 5, 2016);
        Date periodEnd = generateDate(15, 8, 2016);

        ExpenseReport expenseReport = givenAPendingExpenseReport(assignment, periodStart, periodEnd);

        Expense expense = givenAnExpense(expenseReport);

        ResultActions resultActions = whenGettingExpense(webApplicationUser, expense);

        thenExpectExpense(resultActions, webApplicationUser, expense);
    }

    private ResultActions whenGettingExpense(WebApplicationUser webApplicationUser,
                                             Expense expense) throws Exception {

        when(expenseQueryService.getExpense(expense.getId())).thenReturn(empty());

        return mockMvc.perform(get("/expenses/{expenseId}", expense.getId())
                .with(user(webApplicationUser))
                .contentType(HAL_JSON))
                .andDo(print());
    }

    private void thenExpectExpense(ResultActions resultActions, WebApplicationUser webApplicationUser, Expense expense) throws Exception {
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(authenticated()
                    .withUsername(webApplicationUser.getUsername())
                    .withRoles("EXPENSE_USER"));

        verify(expenseQueryService).getExpense(expense.getId());
    }
}
