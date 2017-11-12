package com.tpg.brks.ms.expenses.integration.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ExpenseReportMatcher extends TypeSafeDiagnosingMatcher<ExpenseReport> {
    public static ExpenseReportMatcher expenseReportMatches(ExpenseReport value) {
        return new ExpenseReportMatcher(value);
    }

    private ExpenseReport value;

    public ExpenseReportMatcher(ExpenseReport value) {

        this.value = value;
    }

    @Override
    protected boolean matchesSafely(ExpenseReport expenseReport, Description description) {

        return value.equals(expenseReport);
    }

    @Override
    public void describeTo(Description description) {

        description.appendText(String.format("expenseReportMatches expense report %s", value.getDescription()));
    }
}
