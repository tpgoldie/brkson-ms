package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;

import java.util.List;

public interface ExpenseReportQueryService {
    List<ExpenseReport> getExpenseReportsForAssignment(Long assignmentId);
}