package com.tpg.brks.ms.expenses.domain;

import com.tpg.brks.ms.expenses.utils.DateFormatting;
import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.ExpenseReportStatus.PENDING;

public interface ExpenseReportFixture extends DateFormatting {
    default ExpenseReport anExpenseReport(Assignment assignment, Long id, String description, Date periodStart,
                                          Date periodEnd, ExpenseReportStatus status) {
        ExpenseReport expenseReport = new ExpenseReport();
        expenseReport.setAssignment(assignment);
        expenseReport.setId(id);
        expenseReport.setDescription(description);
        expenseReport.setPeriodStart(toDdMmYyyyFormat(periodStart));
        expenseReport.setPeriodEnd(toDdMmYyyyFormat(periodEnd));
        expenseReport.setStatus(status);

        return expenseReport;
    }

    default ExpenseReport aPendingExpenseReport(Assignment assignment, Long id, String description, Date periodStart,
                                          Date periodEnd) {
        return anExpenseReport(assignment, id, description, periodStart, periodEnd, PENDING);
    }
}
