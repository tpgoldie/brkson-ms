package com.tpg.brks.ms.expenses.web.controllers;

import com.tpg.brks.ms.expenses.domain.*;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ViewExpenseTest extends BaseGivenTest {

    @Test
    @WithUserDetails(value = "jdoe")
    public void getExpense_getRequest_shouldReturnExpense() throws Exception {
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

        when(expenseQueryService.getExpense(expense.getId())).thenReturn(of(expense));

        return mockMvc.perform(get("/expenses/{expenseId}", expense.getId())
                .with(user(webApplicationUser))
                .contentType(HAL_JSON))
                .andDo(print());
    }

    private void thenExpectExpense(ResultActions resultActions, WebApplicationUser webApplicationUser, Expense expense) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(authenticated()
                        .withUsername(webApplicationUser.getUsername())
                        .withRoles("EXPENSE_USER"))
                .andExpect(jsonPath("$.id", is(expense.getId().intValue())))
                .andExpect(jsonPath("$.description", is(expense.getDescription())))
                .andExpect(jsonPath("$.dateEntered", is(expense.getDateEntered())))
                .andExpect(jsonPath("$.expenseDate", is(expense.getExpenseDate())))
                .andExpect(jsonPath("$.status", is(expense.getStatus().name())))
                .andExpect(jsonPath("$.expenseType", is(expense.getExpenseType().name())))
                .andExpect(jsonPath("$.amount", is(expense.getAmount().doubleValue())));

        verify(expenseQueryService).getExpense(expense.getId());
    }
}
