package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;

import java.util.List;
import java.util.Optional;

public interface ExpenseReportQueryService {
    List<ExpenseReport> getExpenseReportsForAssignment(Long assignmentId);

    Optional<ExpenseReport> getExpenseReport(Long reportId);
}
