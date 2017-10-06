package com.tpg.brks.ms.expenses.domain;

import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseReportStatus.PENDING;
import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;
import static java.util.Collections.singletonList;

public interface Given extends DateGeneration, AccountFixture, AssignmentFixture, ExpenseReportFixture, ExpenseFixture {
    Long ID = 101L;

    default Account givenAnAccount() {
        return johnDoeAccount();
    }

    default Assignment givenAnAssignment(Account account) {
        return anOpenAssignment(account, ID,"Assignment 1", generateDate(15, 4, 2016),
                generateDate(15, 4, 2017));
    }

    default List<ExpenseReport> givenExpenseReports(Assignment assignment, Date periodStart, Date periodEnd) {
        ExpenseReport report = aPendingExpenseReport(assignment, ID, "report 1", periodStart, periodEnd);

        report.setExpenses(singletonList(aPendingExpense(report.getId(),"expense 1", generateDate(15, 4, 2017),
                generateDate(13, 4, 2017), SUBSISTENCE, new BigDecimal("250.00"))));

        return singletonList(report);
    }

    default ExpenseReport givenAPendingExpenseReport(Assignment assignment, Date periodStart, Date periodEnd) {
        ExpenseReport report = aPendingExpenseReport(assignment, ID, "report 1", periodStart, periodEnd);

        Expense expense = givenAnExpense(report);

        report.setExpenses(singletonList(expense));

        return report;
    }

    default Expense givenAnExpense(ExpenseReport expenseReport) {
        return aPendingExpense(expenseReport.getId(), "expense 1", generateDate(18, 10, 2016),
                generateDate(18, 10, 2016), SUBSISTENCE, new BigDecimal("215.64"));
    }
}
