package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tpg.brks.ms.expenses.domain.ExpenseReportStatus.OPEN;
import static java.util.Collections.emptyList;

public interface ExpenseReportFixture {
    default ExpenseReportEntity anOpenExpenseReport(AssignmentEntity assignment, String descripton, List<ExpenseEntity> expenses) {
        List<ExpenseEntity> sortedExpenses = expenses.stream().sorted(Comparator.comparing(ExpenseEntity::getExpenseDate)).collect(Collectors.toList());

        Period period = new Period();

        period.setStartDate(sortedExpenses.get(0).getExpenseDate());
        period.setEndDate(sortedExpenses.get(sortedExpenses.size() - 1).getExpenseDate());

        ExpenseReportEntity entity = new ExpenseReportEntity();
        entity.setAssignment(assignment);
        entity.setExpenses(expenses);
        entity.setPeriod(period);
        entity.setStatus(OPEN);

        entity.setDescription(descripton);

        return entity;
    }

    default ExpenseReportEntity anOpenExpenseReport(AssignmentEntity assignment, String descripton,
                                                    Date periodStart, Date periodEnd) {
        Period period = new Period();

        period.setStartDate(periodStart);
        period.setEndDate(periodEnd);

        ExpenseReportEntity entity = new ExpenseReportEntity();
        entity.setAssignment(assignment);
        entity.setExpenses(emptyList());
        entity.setPeriod(period);
        entity.setStatus(OPEN);

        entity.setDescription(descripton);

        return entity;
    }
}
