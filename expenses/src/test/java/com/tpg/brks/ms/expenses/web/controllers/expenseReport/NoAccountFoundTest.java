package com.tpg.brks.ms.expenses.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import org.junit.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static java.util.Optional.empty;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoAccountFoundTest extends ExpenseReportControllerTest {
    @Test
    @WithUserDetails(value = "jdoe")
    public void handleViewExpensesRequest_getRequest_shouldReturnExpenseReportSummary() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        ResultActions resultActions = whenSendRequestForViewingExpenseReports(webApplicationUser);

        thenResponseIsNotFound(resultActions, webApplicationUser);
    }

    private ResultActions whenSendRequestForViewingExpenseReports(WebApplicationUser webApplicationUser) throws Exception {
        when(accountQueryService.findAccountByUsername(webApplicationUser.getUsername())).thenReturn(empty());

        return mockMvc.perform(get("/expenseReports")
                .with(user(webApplicationUser))
                .contentType(HAL_JSON))
                .andDo(print());
    }

    private void thenResponseIsNotFound(ResultActions resultActions, WebApplicationUser webApplicationUser) throws Exception {
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(authenticated()
                .withUsername(webApplicationUser.getUsername())
                .withRoles("EXPENSE_USER"));

        verify(accountQueryService).findAccountByUsername(webApplicationUser.getUsername());
    }
}
