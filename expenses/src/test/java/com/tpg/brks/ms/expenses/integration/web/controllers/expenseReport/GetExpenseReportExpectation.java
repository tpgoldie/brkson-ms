package com.tpg.brks.ms.expenses.integration.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResource;
import org.springframework.http.ResponseEntity;

import static com.tpg.brks.ms.expenses.integration.web.controllers.expenseReport.ExpenseReportMatcher.expenseReportMatches;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetExpenseReportExpectation {
    private ResponseEntity<ExpenseReportResource> expenseReportResourceResponseEntity;

    static GetExpenseReportExpectation then() {
        return new GetExpenseReportExpectation();
    }

    public GetExpenseReportExpectation expectResponse(ResponseEntity<ExpenseReportResource> expenseReportResourceResponseEntity) {

        this.expenseReportResourceResponseEntity = expenseReportResourceResponseEntity;

        return this;
    }

    public GetExpenseReportExpectation and() { return this; }

    public GetExpenseReportExpectation contentIs(ExpenseReport expenseReport) {

        ExpenseReportResource body = expenseReportResourceResponseEntity.getBody();

        ExpenseReport actual = body.getContent();

        expenseReportMatches(actual);
        return this;
    }

    public GetExpenseReportExpectation status(int status) {
        assertThat(expenseReportResourceResponseEntity.getStatusCode(), is(status));

        return this;
    }
}
