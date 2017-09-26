package com.tpg.brks.ms.expenses.domain;

import java.util.Date;
import java.util.List;

public interface Given {

    Account givenAnAccount();

    Assignment givenAnAssignment(Account account);

    List<ExpenseReport> givenExpenseReports(Assignment assignment, Date periodStart, Date periodEnd);
}
