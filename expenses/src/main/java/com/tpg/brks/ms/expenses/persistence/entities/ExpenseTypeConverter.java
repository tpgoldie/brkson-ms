package com.tpg.brks.ms.expenses.persistence.entities;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Optional;

import static com.tpg.brks.ms.expenses.persistence.entities.ExpenseType.NOT_DEFINED;

@Converter
public class ExpenseTypeConverter implements AttributeConverter<ExpenseType, String> {
    @Override
    public String convertToDatabaseColumn(ExpenseType expenseType) {
        return expenseType.getLabel();
    }

    @Override
    public ExpenseType convertToEntityAttribute(String value) {
        Optional<ExpenseType> found = Arrays.stream(ExpenseType.values()).filter(et -> et.getLabel().equals(value)).findFirst();

        return found.orElse(NOT_DEFINED);
    }
}
