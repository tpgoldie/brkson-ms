package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportStatus.OPEN;

public interface ExpenseReportFixture {
    default ExpenseReportEntity anExpenseReport(AssignmentEntity assignment, String descripton, List<ExpenseEntity> expenses) {
        List<ExpenseEntity> sortedExpenses = expenses.stream().sorted(Comparator.comparing(ExpenseEntity::getExpenseDate)).collect(Collectors.toList());

        ExpenseReportEntity entity = ExpenseReportEntity.builder()
            .assignment(assignment)
            .expenses(expenses)
            .periodStart(sortedExpenses.get(0).getExpenseDate())
            .periodEnd(sortedExpenses.get(sortedExpenses.size() - 1).getExpenseDate())
            .status(OPEN)
            .build();

        entity.setDescription(descripton);

        return entity;
    }
}
