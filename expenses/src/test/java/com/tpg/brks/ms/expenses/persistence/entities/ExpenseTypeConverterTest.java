package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.ExpenseType;
import org.junit.Test;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.NOT_DEFINED;
import static com.tpg.brks.ms.expenses.domain.ExpenseType.TRAINING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExpenseTypeConverterTest {
    private ExpenseTypeConverter converter = new ExpenseTypeConverter();

    @Test
    public void convertToDatabaseColumn() {
        String actual = converter.convertToDatabaseColumn(ExpenseType.SUBSISTENCE);

        assertThat(actual, is("Subsistence"));
    }

    @Test
    public void convertFromDatabaseColumn() {
        ExpenseType actual = converter.convertToEntityAttribute("Training");

        assertThat(actual, is(TRAINING));
    }

    @Test
    public void convertFromDatabaseColumnFails() {
        ExpenseType actual = converter.convertToEntityAttribute("undefined!");

        assertThat(actual, is(NOT_DEFINED));
    }
}
