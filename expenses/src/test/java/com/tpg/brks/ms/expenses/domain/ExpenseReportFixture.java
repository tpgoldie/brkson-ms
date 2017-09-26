package com.tpg.brks.ms.expenses.domain;

import com.tpg.brks.ms.utils.DateGeneration;

import java.util.Date;

public interface ExpenseReportFixture extends DateGeneration {
    default ExpenseReport anExpenseReport(Assignment assignment, String description, Date periodStart, Date periodEnd, String status) {
        return ExpenseReport.builder()
            .id(1L)
            .assignment(assignment)
            .description(description)
            .periodStart(toDdMmYyyyFormat(periodStart))
            .periodEnd(toDdMmYyyyFormat(periodEnd))
            .status(status)
                .build();
    }
}
