package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface ExpenseReportFixture {
    default ExpenseReportEntity anExpenseReport(AssignmentEntity assignment, List<ExpenseEntity> expenses) {
        List<ExpenseEntity> sortedExpenses = expenses.stream().sorted(Comparator.comparing(ExpenseEntity::getExpenseDate)).collect(Collectors.toList());
        return ExpenseReportEntity.builder()
                .assignment(assignment)
                .expenses(expenses)
                .periodStart(sortedExpenses.get(0).getExpenseDate())
                .periodEnd(sortedExpenses.get(sortedExpenses.size() - 1).getExpenseDate())
                .build();
    }
}
