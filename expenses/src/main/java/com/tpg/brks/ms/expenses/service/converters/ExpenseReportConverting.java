package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.springframework.core.convert.converter.Converter;

public interface ExpenseReportConverting extends Converter<ExpenseReportEntity, ExpenseReport> {
    @Override
    ExpenseReport convert(ExpenseReportEntity expenseReportEntity);
}
