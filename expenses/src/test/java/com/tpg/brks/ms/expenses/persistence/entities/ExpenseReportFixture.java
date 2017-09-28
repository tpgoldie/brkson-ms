package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.tpg.brks.ms.expenses.domain.ExpenseReportStatus.OPEN;

public interface ExpenseReportFixture {
    default ExpenseReportEntity anExpenseReport(AssignmentEntity assignment, String descripton, List<ExpenseEntity> expenses) {
        List<ExpenseEntity> sortedExpenses = expenses.stream().sorted(Comparator.comparing(ExpenseEntity::getExpenseDate)).collect(Collectors.toList());

        Period period = new Period();

        period.setStartDate(sortedExpenses.get(0).getExpenseDate());
        period.setEndDate(sortedExpenses.get(sortedExpenses.size() - 1).getExpenseDate());

        ExpenseReportEntity entity = ExpenseReportEntity.builder()
            .assignment(assignment)
            .expenses(expenses)
            .period(period)
            .status(OPEN)
            .build();

        entity.setDescription(descripton);

        return entity;
    }
}
