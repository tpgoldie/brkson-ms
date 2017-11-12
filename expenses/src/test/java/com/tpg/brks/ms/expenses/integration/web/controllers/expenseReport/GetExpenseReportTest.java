package com.tpg.brks.ms.expenses.integration.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.integration.web.HttpResponseFixture;
import com.tpg.brks.ms.expenses.integration.web.IntegrationTest;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.persistence.integration.Pair;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResource;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Date;

import static com.tpg.brks.ms.expenses.integration.web.controllers.expenseReport.GetExpenseReportExpectation.then;
import static org.springframework.http.HttpMethod.GET;

public class GetExpenseReportTest extends IntegrationTest implements DateGeneration, HttpResponseFixture {

    @Test
    @WithUserDetails(value = "jdoe")
    public void getExpenseReport_getRequest_shouldReturnExpenseReport() throws Exception {
        WebApplicationUser webApplicationUser = givenAWebApplicationUser();

        Pair<AccountEntity, Account> accountPair = accountIntegrationGiven.givenAnAccount();

        Pair<AssignmentEntity, Assignment> pair = assignmentIntegrationGiven.givenACurrentAssignment(accountPair.getSecond());

        Date periodStart = generateDate(13, 5, 2016);
        Date periodEnd = generateDate(15, 8, 2016);

        Pair<ExpenseReportEntity, ExpenseReport> expenseReportPair = expenseReportIntegrationGiven.givenAnExpenseReport(pair.getFirst(), periodStart, periodEnd);

        ResponseEntity<ExpenseReportResource> actual = whenGettingExpenseReport(expenseReportPair.getSecond());

        then()
            .expectResponse(actual)
                .status(isOk())
            .and()
                .contentIs(expenseReportPair.getSecond());
    }

    private ResponseEntity<ExpenseReportResource> whenGettingExpenseReport(ExpenseReport expenseReport) throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        return restTemplate.exchange(createUrl(String.format("/expenseReports/%d", expenseReport.getId())), GET, entity,
                ExpenseReportResource.class);
    }
}
