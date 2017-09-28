package com.tpg.brks.ms.expenses.domain;

import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.util.Date;
import java.util.List;

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
}
