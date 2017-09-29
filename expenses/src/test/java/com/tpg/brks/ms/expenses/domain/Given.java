package com.tpg.brks.ms.expenses.domain;

import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseReportStatus.PENDING;
import static java.util.Collections.singletonList;

public interface Given extends DateGeneration, AccountFixture, AssignmentFixture, ExpenseReportFixture {
    Long ID = 101L;

    default Account givenAnAccount() {
        return johnDoeAccount();
    }

    default Assignment givenAnAssignment(Account account) {
        return anOpenAssignment(account, ID,"Assignment 1", generateDate(15, 4, 2016),
                generateDate(15, 4, 2017));
    }

    default List<ExpenseReport> givenExpenseReports(Assignment assignment, Date periodStart, Date periodEnd) {
        return singletonList(aPendingExpenseReport(assignment, ID, "report 1", periodStart, periodEnd));
    }

    default ExpenseReport givenAnExpenseReport(Assignment assignment) {
        return anExpenseReport(assignment, 101L, "report 1", generateDate(10, 11, 2016),
                generateDate(10, 12, 2016), PENDING);
    }
}
