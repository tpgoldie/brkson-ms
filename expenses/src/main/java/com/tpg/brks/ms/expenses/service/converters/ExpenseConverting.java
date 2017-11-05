package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.springframework.core.convert.converter.Converter;

public interface ExpenseConverting extends Converter<ExpenseEntity, Expense> {
    @Override
    Expense convert(ExpenseEntity expenseEntity);
}
