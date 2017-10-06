package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.web.BaseGivenTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@ActiveProfiles({"dev"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {"spring.session.store-type=NONE"})
public class ExpenseResourceAssemblerTest extends BaseGivenTest {

    private ExpenseResourceAssembler assembler;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        assembler = new ExpenseResourceAssembler();
    }

    @Test
    public void toResource() {
        Account account = givenAnAccount();

        Assignment assignment = givenAnAssignment(account);

        Date periodStart = generateDate(15, 6, 2016);
        Date periodEnd = generateDate(15, 6, 2017);

        ExpenseReport expenseReport = givenAPendingExpenseReport(assignment, periodStart, periodEnd);

        ExpenseResource actual = whenConvertingToResource(expenseReport.getExpenses().get(0));

        thenResourceGenerated(actual, expenseReport.getExpenses().get(0));
    }

    private ExpenseResource whenConvertingToResource(Expense expense) {
        return assembler.toResources(singletonList(expense)).get(0);
    }

    private void thenResourceGenerated(ExpenseResource actualResource, Expense expectedExpense) {
        assertThat(actualResource.getLinks(), hasSize(1));

        assertThat(actualResource.getLinks().get(0), hasProperty("rel",
                is("self")));

        assertThat(actualResource.getLinks().get(0), hasProperty("href",
                containsString(String.format("expenses/%d", expectedExpense.getId()))));

        assertThat(actualResource, hasProperty("resourceId",
                is(expectedExpense.getId())));

        assertThat(actualResource, hasProperty("content", is(expectedExpense)));
    }
}
