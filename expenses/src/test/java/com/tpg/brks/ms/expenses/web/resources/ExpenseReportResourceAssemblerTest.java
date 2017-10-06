package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.integration.web.IntegrationGivenTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExpenseReportResourceAssemblerTest extends IntegrationGivenTest {

    private ExpenseReportResourceAssembler assembler;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        assembler = new ExpenseReportResourceAssembler();
    }

    @Test
    public void toResource() {
        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(15, 6, 2016);
        Date periodEnd = generateDate(15, 6, 2017);

        ExpenseReport expenseReport = givenAPendingExpenseReport(assignment, periodStart, periodEnd);

        ExpenseReportResource actual = whenConvertingToResource(expenseReport);

        thenResourceGenerated(actual, expenseReport);
    }

    private ExpenseReportResource whenConvertingToResource(ExpenseReport expenseReport) {
        return assembler.toResources(singletonList(expenseReport)).get(0);
    }

    private void thenResourceGenerated(ExpenseReportResource actualResource, ExpenseReport expectedExpenseReport) {
        assertThat(actualResource.getLinks(), hasSize(1));

        assertThat(actualResource.getLinks().get(0), hasProperty("rel",
            is("self")));

        assertThat(actualResource.getLinks().get(0), hasProperty("href",
            containsString(String.format("expenseReports/%d", expectedExpenseReport.getId()))));

        assertThat(actualResource, hasProperty("resourceId",
            is(expectedExpenseReport.getId())));

        assertThat(actualResource, hasProperty("content",
            is(expectedExpenseReport)));

        List<Expense> content = actualResource.getExpenses().stream().map(Resource::getContent).collect(toList());
        assertThat(content, is(expectedExpenseReport.getExpenses()));
    }
}
