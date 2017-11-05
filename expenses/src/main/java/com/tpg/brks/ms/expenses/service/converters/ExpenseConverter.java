package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.utils.DateFormatting;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;

import java.util.Date;

public class ExpenseConverter extends DomainConverter implements ExpenseConverting, DateFormatting {

    public ExpenseConverter() {
        TypeMap<ExpenseEntity, Expense> typeMap = modelMapper.createTypeMap(ExpenseEntity.class, Expense.class);

        typeMap.addMappings(mapper -> mapper.map(src -> toDdMmYyyyFormat(src.getDateEntered()), Expense::setDateEntered));

        Converter<Date, String> dateToString =
                ctx -> ctx.getSource() == null ? null : toDdMmYyyyFormat(ctx.getSource());

        typeMap.addMappings(mapper -> mapper.using(dateToString).map(ExpenseEntity::getExpenseDate, Expense::setExpenseDate));
        typeMap.addMappings(mapper -> mapper.using(dateToString).map(ExpenseEntity::getDateEntered, Expense::setDateEntered));
    }

    @Override
    public Expense convert(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, Expense.class);
    }
}
