package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.web.BaseGivenTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@ActiveProfiles({"dev"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {"spring.session.store-type=NONE"})
public class ExpenseReportResourceAssemblerTest extends BaseGivenTest {

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

        ExpenseReport expenseReport = givenAnExpenseReport(assignment);

        ExpenseReportResource actual = whenConvertingToResource(expenseReport);

        thenResourceGenerated(actual, expenseReport);
    }

    private ExpenseReportResource whenConvertingToResource(ExpenseReport expenseReport) {
        return assembler.toResource(expenseReport);
    }

    private void thenResourceGenerated(ExpenseReportResource actualResource, ExpenseReport expectedExpenseReport) {
        assertThat(actualResource.getLinks(), hasSize(1));

        assertThat(actualResource.getLinks().get(0), hasProperty("rel",
                is("self")));

        assertThat(actualResource, hasProperty("expenseReportId",
                is(expectedExpenseReport.getId())));

        assertThat(actualResource, hasProperty("periodStart",
                is(expectedExpenseReport.getPeriodStart())));

        assertThat(actualResource, hasProperty("periodEnd",
                is(expectedExpenseReport.getPeriodEnd())));

        assertThat(actualResource, hasProperty("status",
                is(expectedExpenseReport.getStatus().name())));
    }
}
